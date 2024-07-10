package me.fodded.proxyloadbalancer.info.network.info.packets;

import lombok.Getter;

import java.util.UUID;

/**
 * The packets are sent from the proxy side to the proxy load balancer to handle all connected players to the proxyloadbalancer and where they are right now
 */
@Getter
public class PlayerJoinPacket extends PacketInfo {

    private final UUID playerUUID;
    private final String serverInstanceName, proxyInstanceName;

    public PlayerJoinPacket(UUID playerUUID, String serverInstanceName, String proxyInstanceName) {
        this.playerUUID = playerUUID;
        this.serverInstanceName = serverInstanceName;
        this.proxyInstanceName = proxyInstanceName;
    }

    public PlayerJoinPacket(String message) {
        PlayerJoinPacket playerJoinPacket = (PlayerJoinPacket) this.deserializePacketInfo(message, PlayerJoinPacket.class);
        this.playerUUID = playerJoinPacket.playerUUID;
        this.serverInstanceName = playerJoinPacket.serverInstanceName;
        this.proxyInstanceName = playerJoinPacket.proxyInstanceName;
    }
}