package me.fodded.apiservice.datatypes;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class DataRegistry {

    @Getter
    private static final DataRegistry instance = new DataRegistry();

    private final Map<String, AbstractData> dataRegistryMap = new HashMap<>();

    public void registryData(AbstractData dataType) {
        this.dataRegistryMap.put(dataType.getDataName(), dataType);
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractData> T getData(String dataName) {
        return (T) this.dataRegistryMap.get(dataName);
    }
}
