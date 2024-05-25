package me.fodded.common;

import lombok.Getter;
import me.fodded.common.data.metrics.ServerMetricsRegistry;
import me.fodded.common.data.metrics.impl.CPUUsageMetrics;
import me.fodded.common.data.metrics.impl.MemoryAllocatedMetrics;
import me.fodded.common.data.metrics.impl.MemoryUsedMetrics;
import me.fodded.common.data.metrics.storage.InfluxStorage;
import me.fodded.common.data.statistics.storage.DataStorageController;
import me.fodded.common.data.statistics.transfer.RedisClient;

@Getter
public class Common {

    private final DataStorageController dataStorageController;

    private ServerMetricsRegistry serverMetricsRegistry;
    private RedisClient redisClient;

    private String serverName;

    @Getter
    private static final Common instance = new Common();

    private Common() {
        this.dataStorageController = new DataStorageController();
        this.dataStorageController.initializeDataStorages();
    }

    public Common initialize(String serverName) {
        this.serverName = serverName;
        return this;
    }

    public Common initializeRedis(String redisHost, int redisPort) {
        this.redisClient = new RedisClient(redisHost, redisPort);
        return this;
    }

    public Common initializeMetrics(InfluxStorage influxStorage) {
        this.serverMetricsRegistry = new ServerMetricsRegistry(influxStorage, serverName);

        this.serverMetricsRegistry.registerMetric(new CPUUsageMetrics());
        this.serverMetricsRegistry.registerMetric(new MemoryAllocatedMetrics());
        this.serverMetricsRegistry.registerMetric(new MemoryUsedMetrics());
        return this;
    }
}