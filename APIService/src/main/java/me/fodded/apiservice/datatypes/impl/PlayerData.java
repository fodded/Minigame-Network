package me.fodded.apiservice.datatypes.impl;

import me.fodded.apiservice.controller.APIKeyController;
import me.fodded.apiservice.datatypes.AbstractData;
import me.fodded.apiservice.exceptions.InvalidKeyException;
import me.fodded.apiservice.exceptions.InvalidStatsRequestException;
import me.fodded.common.data.statistics.DataManager;
import me.fodded.common.data.statistics.GlobalDataRegistry;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class PlayerData extends AbstractData {

    public PlayerData(String dataName) {
        super(dataName);
    }

    @SuppressWarnings("unchecked")
    @Override
    public CompletableFuture<String> processResponse(String[] inputData) throws InvalidKeyException, InvalidStatsRequestException {
        return CompletableFuture.supplyAsync(() -> {
            String dataName = inputData[2];

            UUID seekingPlayerUUID = APIKeyController.getInstance().getUUID(inputData[3], "Could not find player with uuid " + inputData[3]);
            try {
                DataManager dataManager = GlobalDataRegistry.getInstance().getDataManager(dataName);
                if(dataManager == null) {
                    throw new InvalidStatsRequestException("Could not find stats " + dataName);
                }

                Object retrievedData = dataManager.getData(seekingPlayerUUID, false).get();
                if (retrievedData == null) {
                    throw new InvalidStatsRequestException("Could not find user with uuid " + seekingPlayerUUID);
                }

                return GSON.toJson(dataManager);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public String getUsageExample() {
        return "/apikey/playerdata/profile/UUID";
    }
}
