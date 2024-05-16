package me.fodded.proxyloadbalancer.servers;

import net.md_5.bungee.api.ProxyServer;

import java.net.InetSocketAddress;
import java.util.UUID;

public class ProxyInstance extends ServerInstance {

    public ProxyInstance(UUID proxyUUID) {
        super(proxyUUID);
    }

    public void addServer(String serverName, InetSocketAddress address, String serverMotd, boolean restricted) {
        ProxyServer proxyServer = ProxyServer.getInstance();
        if(ProxyServer.getInstance().getServers().containsKey(serverName)) {
            return;
        }

        proxyServer.getServers().put(serverName, proxyServer.constructServerInfo(serverName, address, serverMotd, restricted));
    }

    public void removeServer(String serverName) {
        ProxyServer proxyServer = ProxyServer.getInstance();
        proxyServer.getServers().remove(serverName);
    }
}
