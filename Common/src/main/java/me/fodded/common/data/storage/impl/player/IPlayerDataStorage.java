package me.fodded.common.data.storage.impl.player;

import me.fodded.common.data.impl.player.AbstractPlayerData;
import me.fodded.common.data.storage.impl.IDataInitializer;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IPlayerDataStorage<T extends AbstractPlayerData> extends IDataInitializer {
    CompletableFuture<T> loadData(UUID uuid, T playerDataClass);
    CompletableFuture<Void> saveData(T playerDataClass);
}
