package me.fodded.networkcontroller.event;

import lombok.Getter;
import net.md_5.bungee.api.plugin.Event;

import java.util.UUID;

@Getter
public class PlayerNetworkChangeServerEvent extends Event {

    private final UUID playerUUID, previousServer, newServer;

    public PlayerNetworkChangeServerEvent(UUID playerUUID, UUID previousServer, UUID newServer) {
        this.playerUUID = playerUUID;
        this.previousServer = previousServer;
        this.newServer = newServer;
    }
}
