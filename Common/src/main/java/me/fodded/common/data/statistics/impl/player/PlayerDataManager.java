package me.fodded.common.data.statistics.impl.player;

import com.github.benmanes.caffeine.cache.Caffeine;
import me.fodded.common.ServerCommon;
import me.fodded.common.data.statistics.DataManager;
import me.fodded.common.data.statistics.GlobalDataRegistry;
import me.fodded.common.data.statistics.storage.impl.player.IPlayerDataStorage;
import me.fodded.proxyloadbalancer.NetworkController;
import me.fodded.proxyloadbalancer.info.network.NetworkInstance;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class PlayerDataManager<K extends UUID, V extends AbstractPlayerData> extends DataManager<K, V> {

    public PlayerDataManager(Function<K, V> defaultData) {
        super(defaultData);
        localCache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(5))
                .buildAsync(this::loadPlayerData);

        GlobalDataRegistry.getInstance().registerData(this);
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

    public CompletableFuture<Void> updateData(K key, Consumer<V> consumerAction) {
        return getData(key, true).thenAcceptAsync(data -> {
            if(redisCache.containsKey(key)) {
                updateCacheData(key, data, consumerAction);
            } else {
                updatePersistentData(data);
            }
        }, EXECUTOR_SERVICE);
    }

    public CompletableFuture<Void> savePlayerData(K key) {
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
     * <p>
     * If the player is still on the network, but just switching servers,
     * then we want to remove local cache data, but keep the shared redis data
     */
    public void invalidatePlayerData(K key, int delaySeconds) {
        invalidateLocalData(key);
        SCHEDULED_EXECUTOR_SERVICE.schedule(() -> {
            NetworkInstance networkInstance = NetworkController.getInstance().getNetworkInstance();
            networkInstance.getNetworkPlayer(key).ifPresent(networkPlayer -> {
                invalidateRemoteCache(key);
            });
        }, delaySeconds, TimeUnit.SECONDS);
    }

    private void updateCacheData(K key, V data, Consumer<V> consumerAction) {
        consumerAction.accept(data);

        redisCache.put(key, data);
        localCache.synchronous().refresh(key);
    }

    private void updatePersistentData(V data) {
        IPlayerDataStorage<V> playerDataStorage = ServerCommon.getInstance().getAbstractStorageController().getStorageType(IPlayerDataStorage.class);
        playerDataStorage.saveData(data);
    }

    private V getRemotePlayerData(K key) {
        V redissonValue = redisCache.get(key);
        if(redissonValue != null) {
            return redissonValue;
        }

        IPlayerDataStorage<V> playerDataStorage = ServerCommon.getInstance().getAbstractStorageController().getStorageType(IPlayerDataStorage.class);
        V defaultPlayerData = defaultData.apply(key);

        return playerDataStorage.loadData(key, defaultPlayerData).join();
    }
}