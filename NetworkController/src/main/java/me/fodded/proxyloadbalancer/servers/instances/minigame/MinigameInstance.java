package me.fodded.proxyloadbalancer.servers.instances.minigame;

import me.fodded.proxyloadbalancer.servers.instances.ServerInstance;

public class MinigameInstance extends ServerInstance {

    public MinigameInstance(String serverName) {
        super(serverName);
    }

    @Override
    public String getServerTypeName() {
        return "minigame";
    }
}
