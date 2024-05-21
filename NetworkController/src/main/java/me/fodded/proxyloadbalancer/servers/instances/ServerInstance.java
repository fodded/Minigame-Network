package me.fodded.proxyloadbalancer.servers.instances;

import lombok.Getter;
import me.fodded.proxyloadbalancer.info.network.NetworkPlayer;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
public abstract class ServerInstance implements IServerInfo {

    // serverUUID is randomly generated in the "Common" class
    protected final UUID serverUUID;
    protected final Set<NetworkPlayer> players;

    public ServerInstance(UUID serverUUID) {
        this.serverUUID = serverUUID;
        this.players = new HashSet<>();
    }
}