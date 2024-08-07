package me.fodded.proxyloadbalancer.servers.instances;

import net.md_5.bungee.api.ProxyServer;

import java.net.InetSocketAddress;

public class ProxyInstance extends ServerInstance {

    public ProxyInstance(String serverName, InetSocketAddress serverAddress) {
        super(serverName, serverAddress);
    }

    public void addServer(String serverName, InetSocketAddress address, String serverMotd, boolean restricted) {
        ProxyServer proxyServer = ProxyServer.getInstance();
        if (proxyServer.getServers().containsKey(serverName)) {
            return;
        }

        proxyServer.getServers().put(serverName, proxyServer.constructServerInfo(serverName, address, serverMotd, restricted));
    }

    public void removeServer(String serverName) {
        ProxyServer proxyServer = ProxyServer.getInstance();
        proxyServer.getServers().remove(serverName);
    }

    @Override
    public String getServerTypeName() {
        return "proxy";
    }
}