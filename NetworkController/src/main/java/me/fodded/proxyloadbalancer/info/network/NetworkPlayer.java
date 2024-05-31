package me.fodded.proxyloadbalancer.info.network;

import lombok.Data;

import java.util.UUID;

@Data
public class NetworkPlayer {

    private final UUID playerUUID;
    private String serverInstanceName, proxyInstanceName;

    public NetworkPlayer(UUID playerUUID, String serverInstanceName, String proxyInstanceName) {
        this.playerUUID = playerUUID;
        this.serverInstanceName = serverInstanceName;
        this.proxyInstanceName = proxyInstanceName;
    }
}
