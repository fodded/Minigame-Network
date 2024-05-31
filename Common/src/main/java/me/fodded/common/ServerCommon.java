package me.fodded.common;

import lombok.Data;
import lombok.Getter;
import me.fodded.common.data.metrics.ServerMetricsRegistry;
import me.fodded.common.data.statistics.storage.AbstractStorageController;
import me.fodded.common.data.statistics.transfer.RedisClient;

@Data
public class ServerCommon {

    @Getter
    private static final ServerCommon instance = new ServerCommon();

    private AbstractStorageController abstractStorageController;
    private ServerMetricsRegistry serverMetricsRegistry;
    private RedisClient redisClient;

    private String serverName;
}