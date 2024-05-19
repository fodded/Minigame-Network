package me.fodded.network.proxyloadbalancer;

import me.fodded.proxyloadbalancer.info.network.listeners.bungeecord.PlayerConnectListener;

public final class ProxyLoadBalancer extends Plugin {

    @Override
    public void onEnable() {
        this.getProxy().getPluginManager().registerListener(this, new PlayerConnectListener());
    }
}
