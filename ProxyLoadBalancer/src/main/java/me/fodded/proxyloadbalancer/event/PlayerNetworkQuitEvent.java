package me.fodded.proxyloadbalancer.event;

import lombok.Getter;
import net.md_5.bungee.api.plugin.Event;

import java.util.UUID;

@Getter
public class PlayerNetworkQuitEvent extends Event {

    private final UUID playerUUID;

    public PlayerNetworkQuitEvent(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }
}
