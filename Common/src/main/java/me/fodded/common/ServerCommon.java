package me.fodded.common;

import lombok.Data;
import lombok.Getter;
import me.fodded.common.data.metrics.ServerMetricsRegistry;
import me.fodded.common.data.statistics.storage.AbstractStorageController;
import me.fodded.common.data.statistics.transfer.RedisClient;

@Data
public class ServerCommon {

    /**
     * Although ServerCommon class is singleton, it still has to be "created" with ServerBuilder (just setting fields values)
     * It might be a poor design, however it allows to access any field from this class anywhere among the project
     * <p>
     * Most likely it's being used in the Common module as a way to access redisClient field
     */
    @Getter
    private static final ServerCommon instance = new ServerCommon();

    private AbstractStorageController abstractStorageController;
    private ServerMetricsRegistry serverMetricsRegistry;
    private RedisClient redisClient;

    private String serverName;
}