package me.fodded.proxyloadbalancer.servers.instances.minigame;

import me.fodded.proxyloadbalancer.servers.instances.ServerInstance;

import java.net.InetSocketAddress;

public class MinigameInstance extends ServerInstance {

    public MinigameInstance(String serverName, InetSocketAddress serverAddress) {
        super(serverName, serverAddress);
    }

    @Override
    public String getServerTypeName() {
        return "minigame";
    }
}