package me.fodded.proxyloadbalancer.info.network.packets;

import lombok.Getter;

import java.util.UUID;

/**
 * The packets are sent from the proxy side to the proxy load balancer to handle all connected players to the network and where they are right now
 */
@Getter
public class PlayerChangeServerPacket extends PacketInfo {

    private final UUID playerUUID;
    private final UUID currentServerUUID, newServerUUID;

    public PlayerChangeServerPacket(UUID playerUUID, UUID currentServerUUID, UUID newServerUUID) {
        this.playerUUID = playerUUID;
        this.currentServerUUID = currentServerUUID;
        this.newServerUUID = newServerUUID;
    }

    public PlayerChangeServerPacket(String message) {
        PlayerChangeServerPacket playerChangeServerPacket = (PlayerChangeServerPacket) this.deserializePacketInfo(message, PlayerChangeServerPacket.class);
        this.playerUUID = playerChangeServerPacket.playerUUID;
        this.currentServerUUID = playerChangeServerPacket.currentServerUUID;
        this.newServerUUID = playerChangeServerPacket.newServerUUID;
    }
}