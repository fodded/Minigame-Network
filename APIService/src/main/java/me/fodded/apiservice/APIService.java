package me.fodded.apiservice;

import lombok.Getter;
import me.fodded.apiservice.controller.APIKey;
import me.fodded.apiservice.controller.APIKeyBuilder;
import me.fodded.apiservice.datatypes.impl.PlayerData;
import me.fodded.apiservice.server.APIHttpServer;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class APIService {

    @Getter
    private static final APIService instance = new APIService();

    public void initialize(int serverPort) throws IOException {
        registerDataTypes();

        APIHttpServer apiHttpServer = new APIHttpServer(serverPort);
        apiHttpServer.startServer();

        APIKey apiKey = new APIKeyBuilder(UUID.randomUUID())
                .setMaximumRequestsPerMinute(120)
                .setAllowedStatistics(Arrays.asList("playerdata"))
                .build();
    }

    private void registerDataTypes() {
        new PlayerData("playerdata");
    }
}
