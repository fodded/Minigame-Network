package me.fodded.common.data.impl.player;

import com.github.benmanes.caffeine.cache.Caffeine;
import me.fodded.common.Common;
import me.fodded.common.data.impl.DataManager;
import me.fodded.common.data.storage.impl.player.IPlayerDataStorage;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;

public class PlayerDataManager<K extends UUID, V extends AbstractPlayerData> extends DataManager<K, V> {

    public PlayerDataManager(Function<K, V> defaultData) {
        super(defaultData);
        localCache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(5))
                .buildAsync(this::loadPlayerData);
    }

    /**
     * If the data is not present in local cache,
     * it's either retrieved from redis or from persistent database,
     * depending on where it's present at this moment
     */
    @SuppressWarnings("unchecked")
    public CompletableFuture<V> loadPlayerData(K key, Executor executor) {
        return CompletableFuture.supplyAsync(() -> getRemotePlayerData(key), executor);
    }

    @SuppressWarnings("unchecked")
    public CompletableFuture<Void> savePlayerData(K key) {
        return CompletableFuture.runAsync(() -> {
            V playerData = getRemotePlayerData(key);

            IPlayerDataStorage<V> playerDataStorage = Common.getInstance().getDataStorageController().getPlayerDataStorage();
            playerDataStorage.saveData(playerData);
        });
    }

    /**
     * The data is removed from redis and local cache after delay,
     * In case the player is still present on the server, the data is not removed
     *
     * We want to keep data temporarily to make sure that the data does not have to be loaded again
     * When a player is trying to reconnect immediately
     */
    public CompletableFuture<Void> invalidatePlayerData(K key, long delaySeconds) {
        return CompletableFuture.runAsync(() -> {
            invalidateLocalData(key);
            invalidateRemoteCache(key);
        });
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
