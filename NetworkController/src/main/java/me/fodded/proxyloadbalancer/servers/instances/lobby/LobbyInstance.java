package me.fodded.proxyloadbalancer.servers.instances.lobby;

import me.fodded.proxyloadbalancer.servers.instances.ServerInstance;

public class LobbyInstance extends ServerInstance {

    public LobbyInstance(String serverName) {
        super(serverName);
    }

    @Override
    public String getServerTypeName() {
        return "lobby";
    }
}
