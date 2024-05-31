package me.fodded.common.data.statistics.impl.player;

import lombok.Getter;
import me.fodded.common.data.statistics.DataManager;

import java.util.HashMap;
import java.util.Map;

@Getter
public class GlobalDataRegistry {

    @Getter
    private static final GlobalDataRegistry instance = new GlobalDataRegistry();

    private final Map<String, DataManager<?, ?>> registryDataManager = new HashMap<>();
    public void registerData(DataManager<?, ?> dataManager) {
        registryDataManager.put(dataManager.getDataName(), dataManager);
    }

    @SuppressWarnings("unchecked")
    public <T extends DataManager<?, ?>> T getDataManager(String dataManagerName) {
        return (T) registryDataManager.get(dataManagerName);
    }
}
