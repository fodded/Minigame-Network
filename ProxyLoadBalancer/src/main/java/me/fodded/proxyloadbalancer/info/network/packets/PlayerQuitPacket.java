package me.fodded.proxyloadbalancer.info.network.packets;

import lombok.Getter;

import java.util.UUID;

/**
 * The packets are sent from the proxy side to the proxy load balancer to handle all connected players to the network and where they are right now
 */
@Getter
public class PlayerQuitPacket extends PacketInfo {

    private final UUID playerUUID;
    public PlayerQuitPacket(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public PlayerQuitPacket(String message) {
        PlayerQuitPacket playerQuitPacket = (PlayerQuitPacket) this.deserializePacketInfo(message, PlayerQuitPacket.class);
        this.playerUUID = playerQuitPacket.playerUUID;
    }
}