package me.fodded.apiservice.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import me.fodded.apiservice.controller.APIKey;
import me.fodded.apiservice.controller.APIKeyController;
import me.fodded.apiservice.datatypes.DataRegistry;
import me.fodded.apiservice.exceptions.InvalidKeyException;
import me.fodded.apiservice.exceptions.InvalidStatsRequestException;
import me.fodded.apiservice.exceptions.OverlyUsedKeyException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class APIServerHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) {
        CompletableFuture.runAsync(() -> {
            try {
                String responseMessage = getResponseMessage(exchange.getRequestURI().toString());
                OutputStream os = exchange.getResponseBody();
                os.write(responseMessage.getBytes());
                os.close();
                exchange.sendResponseHeaders(200, responseMessage.length());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private String getResponseMessage(String requestUrl) {
        String[] message = requestUrl.split("/");
        if (message.length != 4) {
            return "/apikey/dataname/playeruuid as an example <br> /979f2625-7771-4ee7-9142-277665602cab/profile/c2d3d415-dbdd-4d1a-92a5-33badd11d4be </br>";
        }

        try {
            String stringAPIKeyId = message[1];

            APIKeyController apiKeyController = APIKeyController.getInstance();
            UUID APIKeyId = apiKeyController.getUUID(stringAPIKeyId, "The provided API key is invalid");

            APIKey apiKey = apiKeyController.getAPIKey(APIKeyId);
            apiKey.updateRequestsSent();

            return DataRegistry.getInstance().getData(message[2]).processResponse(message).join();
        } catch (InvalidKeyException | OverlyUsedKeyException | InvalidStatsRequestException exception) {
            return exception.getMessage();
        }
    }
}
