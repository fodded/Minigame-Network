package me.fodded.proxyloadbalancer.servers.instances.lobby;

import me.fodded.proxyloadbalancer.servers.instances.ServerInstance;

import java.net.InetSocketAddress;

public class LobbyInstance extends ServerInstance {

    public LobbyInstance(String serverName, InetSocketAddress serverAddress) {
        super(serverName, serverAddress);
    }

    @Override
    public String getServerTypeName() {
        return "lobby";
    }
}
