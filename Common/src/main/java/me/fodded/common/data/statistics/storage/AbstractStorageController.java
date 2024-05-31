package me.fodded.common.data.statistics.storage;

import me.fodded.common.data.statistics.storage.impl.IDataInitializer;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractStorageController {

    protected final Map<Class<? extends IDataInitializer>, IDataInitializer> storagesMap = new HashMap<>();

    public AbstractStorageController() {
        initializeDataStorages();
    }

    public abstract void initializeDataStorages();

    @SuppressWarnings("unchecked")
    public <T extends IDataInitializer> T getStorageType(Class<? extends IDataInitializer> storageClass) {
       return (T) storagesMap.get(storageClass);
    }
}
