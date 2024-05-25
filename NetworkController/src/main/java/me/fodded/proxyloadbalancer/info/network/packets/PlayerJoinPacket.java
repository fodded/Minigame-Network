package me.fodded.proxyloadbalancer.info.network.packets;

import lombok.Getter;

import java.util.UUID;

/**
 * The packets are sent from the proxy side to the proxy load balancer to handle all connected players to the proxyloadbalancer and where they are right now
 */
@Getter
public class PlayerJoinPacket extends PacketInfo {

    private final UUID playerUUID;
    private final String serverInstanceUUID, proxyInstanceUUID;

    public PlayerJoinPacket(UUID playerUUID, String serverInstanceUUID, String proxyInstanceUUID) {
        this.playerUUID = playerUUID;
        this.serverInstanceUUID = serverInstanceUUID;
        this.proxyInstanceUUID = proxyInstanceUUID;
    }

    public PlayerJoinPacket(String message) {
        PlayerJoinPacket playerJoinPacket = (PlayerJoinPacket) this.deserializePacketInfo(message, PlayerJoinPacket.class);
        this.playerUUID = playerJoinPacket.playerUUID;
        this.serverInstanceUUID = playerJoinPacket.serverInstanceUUID;
        this.proxyInstanceUUID = playerJoinPacket.proxyInstanceUUID;
    }
}