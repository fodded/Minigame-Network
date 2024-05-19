package me.fodded.common.data.statistics;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.google.common.reflect.TypeToken;
import me.fodded.common.Common;
import org.redisson.api.RMap;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Function;

public abstract class DataManager<K, V> implements IDataName {

    protected AsyncLoadingCache<K, V> localCache;
    protected final RMap<K, V> redisCache;

    protected final Function<K, V> defaultData;
    protected final Class<V> dataClass;

    protected static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newScheduledThreadPool(1);

    @SuppressWarnings("unchecked")
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
     *
     * @param shouldCreateNewData
     * if data is not present in case player has never been on the server,
     * then we either create new data for them, or we don't, depending on what value is passed to the parameter
     */
    public CompletableFuture<V> getData(K key, boolean shouldCreateNewData) {
        try {
            return this.localCache.get(key);
        } catch (NullPointerException exception) {
            return shouldCreateNewData ? CompletableFuture.completedFuture(defaultData.apply(key)) : null;
        }
    }

    protected void invalidateLocalData(K key) {
        this.localCache.synchronous().invalidate(key);
    }

    protected void invalidateRemoteCache(K key) {
        this.redisCache.remove(key);
    }
}