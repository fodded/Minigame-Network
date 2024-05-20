package me.fodded.proxyloadbalancer.servers.instances.minigame;

import me.fodded.proxyloadbalancer.servers.instances.ServerInstance;

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
