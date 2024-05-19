package me.fodded.common.data.statistics.impl.player;

import com.github.benmanes.caffeine.cache.Caffeine;
import me.fodded.common.Common;
import me.fodded.common.data.statistics.DataManager;
import me.fodded.common.data.statistics.storage.impl.player.IPlayerDataStorage;
import me.fodded.proxyloadbalancer.info.network.NetworkPlayer;
import me.fodded.proxyloadbalancer.info.network.NetworkPlayerController;

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
     * depending on where it's present at this moment
     *
     * The method is used to put data in cache if it was not found anywhere
     */
    @SuppressWarnings("unchecked")
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

    @SuppressWarnings("unchecked")
    public CompletableFuture<Void> savePlayerData(K key) {
        return CompletableFuture.runAsync(() -> {
            V playerData = getRemotePlayerData(key);

            IPlayerDataStorage<V> playerDataStorage = Common.getInstance().getDataStorageController().getPlayerDataStorage();
            playerDataStorage.saveData(playerData);
        }, EXECUTOR_SERVICE);
    }

    /**
     * The data is removed from redis and local cache after delay,
     * In case the player is still present on the server, the data is not removed
     *
     * We want to keep data temporarily to make sure that the data does not have to be loaded again
     * When a player is trying to reconnect immediately
     */
    public CompletableFuture<Void> invalidatePlayerData(K key, int delaySeconds) {
        return CompletableFuture.runAsync(() -> EXECUTOR_SERVICE.schedule(() -> {
            NetworkPlayer networkPlayer = NetworkPlayerController.getInstance().findNetworkPlayer(key);
            if(networkPlayer != null) {
                return;
            }

            invalidateLocalData(key);
            invalidateRemoteCache(key);
        }, delaySeconds, TimeUnit.SECONDS), EXECUTOR_SERVICE);
    }

    private void updateCacheData(K key, V data, Consumer<V> consumerAction) {
        consumerAction.accept(data);

        redisCache.put(key, data);
        localCache.synchronous().refresh(key);
    }

    @SuppressWarnings("unchecked")
    private void updatePersistentData(V data) {
        IPlayerDataStorage<V> playerDataStorage = Common.getInstance().getDataStorageController().getPlayerDataStorage();
        playerDataStorage.saveData(data);
    }

    @SuppressWarnings("unchecked")
    private V getRemotePlayerData(K key) {
        V redissonValue = redisCache.get(key);
        if(redissonValue != null) {
            return redissonValue;
        }

        IPlayerDataStorage<V> playerDataStorage = Common.getInstance().getDataStorageController().getPlayerDataStorage();
        V defaultPlayerData = defaultData.apply(key);

        return playerDataStorage.loadData(key, defaultPlayerData).join();
    }
}