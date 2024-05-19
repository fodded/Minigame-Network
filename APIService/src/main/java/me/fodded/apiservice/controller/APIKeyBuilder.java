package me.fodded.apiservice.controller;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

public class APIKeyBuilder {

    private final APIKey apiKey;

    public APIKeyBuilder(UUID ownerId) {
        this.apiKey = new APIKey(UUID.randomUUID(), ownerId);
    }

    public APIKeyBuilder setMaximumRequestsPerMinute(int requests) {
        this.apiKey.setMaxRequestsPerMinute(requests);
        return this;
    }

    @SuppressWarnings("unchecked")
    public APIKeyBuilder setAllowedStatistics(Collection<?> collection) {
        this.apiKey.setAllowedData((Set<String>) collection);
        return this;
    }

    public APIKey build() {
        APIKeyController.getInstance().registerAPIKey(apiKey);
        return apiKey;
    }
}
