package me.fodded.proxyloadbalancer.info.network.packets;

import lombok.Getter;

import java.util.UUID;

/**
 * The packets are sent from the proxy side to the proxy load balancer to handle all connected players to the proxyloadbalancer and where they are right now
 */
@Getter
public class PlayerChangeServerPacket extends PacketInfo {

    private final UUID playerUUID;
    private final String currentServerName, newServerName;

    public PlayerChangeServerPacket(UUID playerUUID, String currentServerName, String newServerName) {
        this.playerUUID = playerUUID;
        this.currentServerName = currentServerName;
        this.newServerName = newServerName;
    }

    public PlayerChangeServerPacket(String message) {
        PlayerChangeServerPacket playerChangeServerPacket = (PlayerChangeServerPacket) this.deserializePacketInfo(message, PlayerChangeServerPacket.class);
        this.playerUUID = playerChangeServerPacket.playerUUID;
        this.currentServerName = playerChangeServerPacket.currentServerName;
        this.newServerName = playerChangeServerPacket.newServerName;
    }
}