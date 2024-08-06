package me.fodded.common.data.statistics.impl.player;

import com.github.benmanes.caffeine.cache.Caffeine;
import me.fodded.common.ServerCommon;
import me.fodded.common.data.statistics.DataManager;
import me.fodded.common.data.statistics.GlobalDataRegistry;
import me.fodded.common.data.statistics.storage.impl.player.IPlayerDataStorage;
import org.redisson.api.RMap;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class PlayerDataManager<K extends UUID, V extends AbstractPlayerData> extends DataManager<K, V> {

    // UUID, NetworkPlayer (We can't access NetworkPlayer from this module)
    private static RMap<UUID, ?> networkPlayersMap;

    public PlayerDataManager(Function<K, V> defaultData) {
        super(defaultData);
        localCache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(15))
                .buildAsync(this::loadPlayerData);

        GlobalDataRegistry.getInstance().registerData(this);

        if (networkPlayersMap != null) return;
        networkPlayersMap = ServerCommon.getInstance().getRedisClient().getRedissonClient().getMap("networkPlayerMap");
    }

    /**
     * If the data is not present in local cache,
     * it's either retrieved from redis or from persistent database,
     * depending on where it's present at this moment.
     * <p>
     * The method is used to put data in cache if it was not found anywhere
     */
    private CompletableFuture<V> loadPlayerData(K key, Executor executor) {
        return CompletableFuture.supplyAsync(() -> getRemotePlayerData(key), executor);
    }

    /**
     * Saves the player's data either in local cache and redis, or in a database, depending on where the data is initially present
     *
     * @param key the key based on which you get the data and save it
     * @param consumerAction an action to change data however you want and save after it
     * @return A CompletableFuture after the data has been completely processed
     */
    public CompletableFuture<Void> updateData(K key, Consumer<V> consumerAction) {
        return getData(key, true).thenAcceptAsync(data -> {
            if (redisCache.containsKey(key)) {
                updateCacheData(key, data, consumerAction);
            } else {
                updatePersistentData(data);
            }
        }, EXECUTOR_SERVICE);
    }

    /**
     * Saves the player's data and then after some period removes it from cache, if the player is not on the server anymore
     *
     * @param key the key based on which you get the data and save it
     * @param delaySeconds how many seconds has to pass, to invalidate data from cache, usually around 60 is fine
     * @return A CompletableFuture after the data has been completely processed
     */
    public CompletableFuture<Void> savePlayerData(K key, int delaySeconds) {
        return CompletableFuture.runAsync(() -> {
            V playerData = getRemotePlayerData(key);

            IPlayerDataStorage<V> playerDataStorage = ServerCommon.getInstance().getAbstractStorageController().getStorageType(IPlayerDataStorage.class);
            playerDataStorage.saveData(playerData);
        }, EXECUTOR_SERVICE);
    }

    /**
     * The data is removed from redis and local cache after delay,
     * In case the player is still present on the server, the data is not removed
     * <p>
     * We want to keep data temporarily to make sure that the data does not have to be loaded again
     * When a player is trying to reconnect immediately
     */
    public void invalidatePlayerData(K key, int delaySeconds) {
        SCHEDULED_EXECUTOR_SERVICE.schedule(() -> {
            if (!networkPlayersMap.containsKey(key)) {
                invalidateLocalData(key);
                invalidateRemoteCache(key);
            }
        }, delaySeconds, TimeUnit.SECONDS);
    }

    private void updateCacheData(K key, V data, Consumer<V> consumerAction) {
        consumerAction.accept(data);

        redisCache.put(key, data);
        localCache.synchronous().put(key, data);
    }

    private void updatePersistentData(V data) {
        IPlayerDataStorage<V> playerDataStorage = ServerCommon.getInstance().getAbstractStorageController().getStorageType(IPlayerDataStorage.class);
        playerDataStorage.saveData(data);
    }

    private V getRemotePlayerData(K key) {
        V redissonValue = redisCache.get(key);
        if (redissonValue != null) {
            return redissonValue;
        }

        IPlayerDataStorage<V> playerDataStorage = ServerCommon.getInstance().getAbstractStorageController().getStorageType(IPlayerDataStorage.class);
        V defaultPlayerData = defaultData.apply(key);

        return playerDataStorage.loadData(key, defaultPlayerData).join();
    }
}