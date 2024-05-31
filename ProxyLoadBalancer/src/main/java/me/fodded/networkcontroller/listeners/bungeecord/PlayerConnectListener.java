package me.fodded.networkcontroller.listeners.bungeecord;

import me.fodded.common.ServerCommon;
import me.fodded.networkcontroller.ProxyLoadBalancer;
import me.fodded.proxyloadbalancer.info.network.packets.PlayerJoinPacket;
import me.fodded.proxyloadbalancer.info.network.packets.PlayerQuitPacket;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;


/**
 * Here we let other servers know that a player left the proxyloadbalancer, and we should do appropriate calculations
 */
public class PlayerConnectListener implements Listener {

    @EventHandler
    public void onJoin(ServerConnectEvent event) {
        UUID playerUUID = event.getPlayer().getUniqueId();

        ServerCommon serverCommon = ProxyLoadBalancer.getInstance().getServerCommon();

        String serverInstanceString = event.getTarget().getName();
        String proxyInstanceName = ServerCommon.getInstance().getServerName();

        PlayerJoinPacket playerJoinPacket = new PlayerJoinPacket(playerUUID, serverInstanceString, proxyInstanceName);
        ServerCommon.getInstance().getRedisClient().publishMessageAsync("playerJoin", playerJoinPacket.serializePacketInfo());
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent event) {
        UUID playerUUID = event.getPlayer().getUniqueId();

        PlayerQuitPacket playerQuitPacket = new PlayerQuitPacket(playerUUID);
        ServerCommon.getInstance().getRedisClient().publishMessageAsync("playerQuit", playerQuitPacket.serializePacketInfo());
    }
}
