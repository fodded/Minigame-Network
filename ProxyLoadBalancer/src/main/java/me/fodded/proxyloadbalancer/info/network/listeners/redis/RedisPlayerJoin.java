package me.fodded.proxyloadbalancer.info.network.listeners.redis;

import me.fodded.common.data.transfer.IRedisListener;
import me.fodded.proxyloadbalancer.ProxyLoadBalancer;
import me.fodded.proxyloadbalancer.event.PlayerNetworkJoinEvent;
import me.fodded.proxyloadbalancer.info.network.NetworkPlayer;
import me.fodded.proxyloadbalancer.info.network.packets.PlayerJoinPacket;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * The packets which are sent from proxy side to proxy load balancer are being caught with redis here
 */
public class RedisPlayerJoin implements IRedisListener {

    @Override
    public void onMessage(CharSequence channel, Object msg) {
        String message = (String) msg;
        PlayerJoinPacket playerJoinPacket = new PlayerJoinPacket(message);

        Plugin plugin = ProxyLoadBalancer.getInstance();
        plugin.getProxy().getScheduler().runAsync(plugin, ()-> {
            NetworkPlayer networkPlayer = new NetworkPlayer(playerJoinPacket.getPlayerUUID(), playerJoinPacket.getServerInstanceUUID(), playerJoinPacket.getProxyInstanceUUID());
            ProxyLoadBalancer.getInstance().getNetworkInstance().trackPlayer(networkPlayer);

            plugin.getProxy().getPluginManager().callEvent(new PlayerNetworkJoinEvent(playerJoinPacket.getPlayerUUID()));
        });
    }
}