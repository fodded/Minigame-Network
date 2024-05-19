package me.fodded.proxyloadbalancer.servers.lobby;

import me.fodded.proxyloadbalancer.servers.ServerInstance;

import java.util.UUID;

public class LobbyInstance extends ServerInstance {

    public LobbyInstance(UUID serverUUID) {
        super(serverUUID);
    }

    @Override
    public String getServerTypeName() {
        return "lobby";
    }
}
