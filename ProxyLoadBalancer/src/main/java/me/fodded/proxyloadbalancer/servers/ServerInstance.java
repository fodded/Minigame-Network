package me.fodded.proxyloadbalancer.servers;

import lombok.Getter;
import me.fodded.proxyloadbalancer.info.network.NetworkPlayer;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
public abstract class ServerInstance {

    // serverUUID is randomly generated in the "Common" class
    protected final UUID serverUUID;
    protected final Set<NetworkPlayer> players;

    public ServerInstance(UUID serverUUID) {
        this.serverUUID = serverUUID;
        this.players = new HashSet<>();
    }
}
