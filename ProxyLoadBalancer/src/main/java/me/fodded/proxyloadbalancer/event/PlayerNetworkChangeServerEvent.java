package me.fodded.proxyloadbalancer.event;

import lombok.Getter;
import net.md_5.bungee.api.plugin.Event;

import java.util.UUID;

@Getter
public class PlayerNetworkChangeServerEvent extends Event {

    private final UUID playerUUID;
    private final String previousServer, newServer;

    public PlayerNetworkChangeServerEvent(UUID playerUUID, String previousServer, String newServer) {
        this.playerUUID = playerUUID;
        this.previousServer = previousServer;
        this.newServer = newServer;
    }
}
