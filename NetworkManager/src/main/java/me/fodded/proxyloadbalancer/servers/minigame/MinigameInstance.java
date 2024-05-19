package me.fodded.proxyloadbalancer.servers.minigame;

import me.fodded.proxyloadbalancer.servers.ServerInstance;

import java.util.UUID;

public class MinigameInstance extends ServerInstance {

    public MinigameInstance(UUID serverUUID) {
        super(serverUUID);
    }

    @Override
    public String getServerTypeName() {
        return "minigame";
    }
}
