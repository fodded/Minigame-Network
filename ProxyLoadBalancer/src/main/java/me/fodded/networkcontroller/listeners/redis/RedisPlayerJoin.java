package me.fodded.networkcontroller.listeners.redis;

import me.fodded.common.data.statistics.transfer.IRedisListener;
import me.fodded.networkcontroller.ProxyLoadBalancer;
import me.fodded.networkcontroller.event.PlayerNetworkJoinEvent;
import me.fodded.proxyloadbalancer.NetworkController;
import me.fodded.proxyloadbalancer.info.network.info.packets.PlayerJoinPacket;
import me.fodded.proxyloadbalancer.info.network.info.player.NetworkPlayer;
import net.md_5.bungee.api.ProxyServer;
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
        ProxyServer proxy = plugin.getProxy();

        proxy.getScheduler().runAsync(plugin, () -> {
            NetworkPlayer networkPlayer = new NetworkPlayer(playerJoinPacket.getPlayerUUID(), playerJoinPacket.getServerInstanceName(), playerJoinPacket.getProxyInstanceName());
            NetworkController.getInstance().getNetworkInstance().trackPlayer(networkPlayer);

            proxy.getPluginManager().callEvent(new PlayerNetworkJoinEvent(playerJoinPacket.getPlayerUUID()));
        });
    }
}