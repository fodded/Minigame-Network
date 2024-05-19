package me.fodded.proxyloadbalancer.info.network;

import lombok.Data;

import java.util.UUID;

@Data
public class NetworkPlayer {

    private final UUID playerUUID;
    private UUID serverInstanceId, proxyInstanceId;

    public NetworkPlayer(UUID playerUUID, UUID serverInstanceId, UUID proxyInstanceId) {
        this.playerUUID = playerUUID;
        this.serverInstanceId = serverInstanceId;
        this.proxyInstanceId = proxyInstanceId;
    }
}
