package me.fodded.proxyloadbalancer.info.network.listeners.redis;

import me.fodded.common.data.transfer.IRedisListener;
import me.fodded.proxyloadbalancer.ProxyLoadBalancer;
import me.fodded.proxyloadbalancer.event.PlayerNetworkQuitEvent;
import me.fodded.proxyloadbalancer.info.network.packets.PlayerQuitPacket;
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
        plugin.getProxy().getScheduler().runAsync(plugin, ()-> {
            new PlayerNetworkQuitEvent(playerUUID);
            plugin.getProxy().getPluginManager().callEvent(new PlayerNetworkQuitEvent(playerUUID));
        });
    }
}