package me.fodded.common;

import lombok.Getter;
import me.fodded.common.data.metrics.ServerMetric;
import me.fodded.common.data.metrics.ServerMetricsRegistry;
import me.fodded.common.data.metrics.impl.CPUUsageMetrics;
import me.fodded.common.data.metrics.impl.MemoryAllocatedMetrics;
import me.fodded.common.data.metrics.impl.MemoryUsedMetrics;
import me.fodded.common.data.metrics.storage.InfluxStorage;
import me.fodded.common.data.statistics.storage.AbstractStorageController;
import me.fodded.common.data.statistics.transfer.RedisClient;

@Getter
public class ServerBuilder {

    private final ServerCommon serverCommon;

    public ServerBuilder() {
        this.serverCommon = ServerCommon.getInstance();
    }

    public ServerBuilder initialize(String serverName) {
        this.serverCommon.setServerName(serverName);
        return this;
    }

    public ServerBuilder initializeDataStorage(AbstractStorageController abstractStorageController) {
        this.serverCommon.setAbstractStorageController(abstractStorageController);
        abstractStorageController.initializeDataStorages();
        return this;
    }

    public ServerBuilder initializeRedis(String redisHost, int redisPort) {
        this.serverCommon.setRedisClient(new RedisClient(redisHost, redisPort));
        return this;
    }

    public ServerBuilder initializeMetrics(InfluxStorage influxStorage, ServerMetric... serverMetrics) {
        String serverName = this.serverCommon.getServerName();
        ServerMetricsRegistry serverMetricsRegistry = new ServerMetricsRegistry(influxStorage, serverName);

        serverMetricsRegistry.registerMetric(new CPUUsageMetrics());
        serverMetricsRegistry.registerMetric(new MemoryAllocatedMetrics());
        serverMetricsRegistry.registerMetric(new MemoryUsedMetrics());
        for (ServerMetric serverMetric : serverMetrics) {
            serverMetricsRegistry.registerMetric(serverMetric);
        }

        this.serverCommon.setServerMetricsRegistry(serverMetricsRegistry);
        return this;
    }

    public ServerCommon build() {
        return this.serverCommon;
    }
}
