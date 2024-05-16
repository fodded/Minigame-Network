package me.fodded.common.data.impl;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.google.common.reflect.TypeToken;
import me.fodded.common.Common;
import org.redisson.api.RMap;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class DataManager<K, V> {

    protected AsyncLoadingCache<K, V> localCache;
    protected final RMap<K, V> redisCache;
    protected final Function<K, V> defaultData;

    protected final Class<V> dataClass;

    public DataManager(Function<K, V> defaultData) {
        TypeToken<V> typeToken = new TypeToken<V>(getClass()) {};
        this.dataClass = (Class<V>) typeToken.getRawType();
        this.defaultData = defaultData;

        this.redisCache = Common.getInstance().getRedisClient().getRedissonClient().getMap(this.dataClass.getSimpleName());
    }

    /**
     * Returns data from local cache, if it's present there,
     * otherwise it will attempt to load it from redis or mongodb and put in the cache later
     * The operation is processed asynchronously
     */
    public CompletableFuture<V> getData(K key) {
        try {
            return this.localCache.get(key);
        } catch (NullPointerException exception) {
            return CompletableFuture.completedFuture(defaultData.apply(key));
        }
    }

    public void updateData(K key, Consumer<V> consumerAction) {
        getData(key).thenAccept(data -> {
            consumerAction.accept(data);
        });
    }

    public void saveData(K key) {
        getData(key).thenAccept(data -> {

        });
    }

    public void invalidateLocalData(K key) {
        this.localCache.synchronous().invalidate(key);
    }

    public void invalidateRemoteCache(K key) {
        this.redisCache.remove(key);
    }
}