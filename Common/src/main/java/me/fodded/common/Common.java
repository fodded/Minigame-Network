package me.fodded.common;

import lombok.Getter;
import me.fodded.common.data.storage.DataStorageController;
import me.fodded.common.data.transfer.RedisClient;

import java.util.UUID;

@Getter
public class Common {

    private final DataStorageController dataStorageController;
    private final RedisClient redisClient;

    private final UUID serverUUID = UUID.randomUUID();

    @Getter
    private static final Common instance = new Common();

    private Common() {
        this.dataStorageController = new DataStorageController();
        this.dataStorageController.initializeDataStorages();

        this.redisClient = new RedisClient();
    }
}