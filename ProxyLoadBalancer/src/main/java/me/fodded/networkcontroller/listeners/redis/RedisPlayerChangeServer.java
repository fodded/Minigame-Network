package me.fodded.networkcontroller.listeners.redis;

import me.fodded.common.data.statistics.transfer.IRedisListener;
import me.fodded.networkcontroller.ProxyLoadBalancer;
import me.fodded.networkcontroller.event.PlayerNetworkJoinEvent;
import me.fodded.proxyloadbalancer.NetworkController;
import me.fodded.proxyloadbalancer.info.network.NetworkPlayer;
import me.fodded.proxyloadbalancer.info.network.packets.PlayerChangeServerPacket;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * The packets which are sent from proxy side to proxy load balancer are being caught with redis here
 */
public class RedisPlayerChangeServer implements IRedisListener {

    @Override
    public void onMessage(CharSequence channel, Object msg) {
        String message = (String) msg;
        PlayerChangeServerPacket playerChangeServerPacket = new PlayerChangeServerPacket(message);

        Plugin plugin = ProxyLoadBalancer.getInstance();
        plugin.getProxy().getScheduler().runAsync(plugin, ()-> {
            NetworkPlayer networkPlayer = NetworkController.getInstance().getNetworkInstance().getNetworkPlayer(playerChangeServerPacket.getPlayerUUID());
            networkPlayer.setServerInstanceId(playerChangeServerPacket.getNewServerUUID());

            plugin.getProxy().getPluginManager().callEvent(new PlayerNetworkJoinEvent(networkPlayer.getPlayerUUID()));
        });
    }
}
