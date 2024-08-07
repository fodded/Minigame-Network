package me.fodded.skywarsgame.data.storages;

import me.fodded.common.data.statistics.storage.AbstractStorageController;
import me.fodded.common.data.statistics.storage.impl.player.IPlayerDataStorage;
import me.fodded.common.data.statistics.storage.impl.player.PlayerMongoStorage;

public class SkywarsStorageController extends AbstractStorageController {

    @Override
    public void initializeDataStorages() {
        IPlayerDataStorage<?> playerDataStorage = new PlayerMongoStorage<>();
        playerDataStorage.initialize();

        this.storagesMap.put(IPlayerDataStorage.class, playerDataStorage);
    }
}
