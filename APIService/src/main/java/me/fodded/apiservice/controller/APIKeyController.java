package me.fodded.apiservice.controller;

import lombok.Getter;
import me.fodded.apiservice.exceptions.InvalidKeyException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class APIKeyController {

    @Getter
    private static final APIKeyController instance = new APIKeyController();

    // The key is API's UUID
    private final Map<UUID, APIKey> registeredKeys = new HashMap<>();

    public void registerAPIKey(APIKey apiKey) {
        registeredKeys.put(apiKey.getApiKeyID(), apiKey);
    }

    public APIKey getAPIKey(UUID apiKeyId) throws InvalidKeyException {
        try {
            return registeredKeys.get(apiKeyId);
        } catch (NullPointerException exception) {
            throw new InvalidKeyException("The provided API key is invalid");
        }
    }

    public UUID getUUID(String stringUUID, String errorMessage) throws InvalidKeyException {
        try {
            return UUID.fromString(stringUUID);
        } catch (IllegalArgumentException e) {
            throw new InvalidKeyException(errorMessage);
        }
    }
}
