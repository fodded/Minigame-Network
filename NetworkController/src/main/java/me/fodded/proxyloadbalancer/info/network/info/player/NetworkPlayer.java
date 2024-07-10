package me.fodded.proxyloadbalancer.info.network.info.player;

import lombok.Data;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

import java.util.UUID;

@Data
public class NetworkPlayer {

    private final UUID playerUUID;
    private String serverInstanceName, proxyInstanceName;

    public NetworkPlayer(UUID playerUUID, String serverInstanceName, String proxyInstanceName) {
        this.playerUUID = playerUUID;
        this.serverInstanceName = serverInstanceName;
        this.proxyInstanceName = proxyInstanceName;
    }

    public void sendToServer(String serverInstanceName) {
        this.serverInstanceName = serverInstanceName;

        ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(serverInstanceName);
        ProxyServer.getInstance().getPlayer(playerUUID).connect(serverInfo);
    }
}
