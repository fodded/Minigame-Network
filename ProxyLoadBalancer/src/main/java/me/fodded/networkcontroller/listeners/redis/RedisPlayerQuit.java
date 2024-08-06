package me.fodded.networkcontroller.listeners.redis;

import me.fodded.common.data.statistics.transfer.IRedisListener;
import me.fodded.networkcontroller.ProxyLoadBalancer;
import me.fodded.networkcontroller.event.PlayerNetworkQuitEvent;
import me.fodded.proxyloadbalancer.NetworkController;
import me.fodded.proxyloadbalancer.info.network.NetworkInstance;
import me.fodded.proxyloadbalancer.info.network.info.packets.PlayerQuitPacket;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.UUID;

/**
 * The packets which are sent from proxy side to proxy load balancer are being caught with redis here
 */
public class RedisPlayerQuit implements IRedisListener {

    @Override
    public void onMessage(CharSequence channel, Object msg) {
        String message = (String) msg;

        PlayerQuitPacket playerQuitPacket = new PlayerQuitPacket(message);
        UUID playerUUID = playerQuitPacket.getPlayerUUID();

        Plugin plugin = ProxyLoadBalancer.getInstance();
        ProxyServer proxy = plugin.getProxy();

        proxy.getScheduler().runAsync(plugin, () -> {
            NetworkInstance networkInstance = NetworkController.getInstance().getNetworkInstance();
            networkInstance.getNetworkPlayer(playerUUID).ifPresent(networkPlayer -> {
                networkInstance.untrackPlayer(networkPlayer);
                proxy.getPluginManager().callEvent(new PlayerNetworkQuitEvent(playerUUID));
            });
        });
    }
}