package me.fodded.common.data.storage;

import lombok.Getter;
import me.fodded.common.data.storage.impl.map.IMapDataStorage;
import me.fodded.common.data.storage.impl.player.IPlayerDataStorage;
import me.fodded.common.data.storage.impl.player.PlayerMongoStorage;

@Getter
public class DataStorageController {

    private IPlayerDataStorage playerDataStorage;
    private IMapDataStorage mapDataStorage;

    public void initializeDataStorages() {
        this.playerDataStorage = new PlayerMongoStorage();
        this.playerDataStorage.initialize();
    }
}
