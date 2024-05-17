package me.fodded.common.data.statistics.storage;

import lombok.Getter;
import me.fodded.common.data.statistics.storage.impl.map.IMapDataStorage;
import me.fodded.common.data.statistics.storage.impl.player.IPlayerDataStorage;
import me.fodded.common.data.statistics.storage.impl.player.PlayerMongoStorage;

@Getter
public class DataStorageController {

    private IPlayerDataStorage playerDataStorage;
    private IMapDataStorage mapDataStorage;

    public void initializeDataStorages() {
        this.playerDataStorage = new PlayerMongoStorage();
        this.playerDataStorage.initialize();
    }
}
