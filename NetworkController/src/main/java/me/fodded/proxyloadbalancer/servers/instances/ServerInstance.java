package me.fodded.proxyloadbalancer.servers.instances;

import lombok.Getter;
import me.fodded.proxyloadbalancer.info.network.NetworkPlayer;

import java.util.HashSet;
import java.util.Set;

@Getter
public abstract class ServerInstance implements IServerInfo {

    // serverUUID is randomly generated in the "Common" class
    protected final String serverName;
    protected final Set<NetworkPlayer> players;

    public ServerInstance(String serverName) {
        this.serverName = serverName;
        this.players = new HashSet<>();
    }
}
