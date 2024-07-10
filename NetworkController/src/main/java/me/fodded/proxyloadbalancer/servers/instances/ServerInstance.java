package me.fodded.proxyloadbalancer.servers.instances;

import lombok.Getter;
import me.fodded.proxyloadbalancer.info.network.info.player.NetworkPlayer;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

@Getter
public abstract class ServerInstance implements IServerInfo {

    protected final InetSocketAddress serverAddress;
    protected final String serverName;
    protected final Set<NetworkPlayer> players;

    public ServerInstance(String serverName, InetSocketAddress serverAddress) {
        this.serverName = serverName;
        this.serverAddress = serverAddress;
        this.players = new HashSet<>();
    }
}
