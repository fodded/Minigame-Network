package me.fodded.apiservice.controller;

import lombok.Data;
import me.fodded.apiservice.exceptions.OverlyUsedKeyException;

import java.util.*;

/**
 * The api key is supposed to be created by a player
 */
@Data
public class APIKey {

    private UUID apiKeyID, ownerID;
    private int maxRequestsPerMinute = 120;

    private Set<String> allowedData = new HashSet<>(Arrays.asList("playerdata"));
    private transient final List<Long> lastSentRequests = new LinkedList<>();

    public APIKey(UUID apiKeyID, UUID ownerID) {
        this.apiKeyID = apiKeyID;
        this.ownerID = ownerID;
    }

    public void updateRequestsSent() throws OverlyUsedKeyException {
        if(lastSentRequests.size() > maxRequestsPerMinute) {
            throw new OverlyUsedKeyException("You're exceeding the rate limit, please slow down");
        }

        long currentTime = System.currentTimeMillis();
        lastSentRequests.add(currentTime);
        lastSentRequests.removeIf(lastRequestTime -> lastRequestTime + 1000 < currentTime);
    }
}
