package me.fodded.proxyloadbalancer.info.network.listeners.bungeecord;

import me.fodded.common.Common;
import me.fodded.proxyloadbalancer.info.network.packets.PlayerJoinPacket;
import me.fodded.proxyloadbalancer.info.network.packets.PlayerQuitPacket;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;


/**
 * Here we let other servers know that a player left the network, and we should do appropriate calculations
 */
public class PlayerConnectListener implements Listener {

    @EventHandler
    public void onJoin(ServerConnectEvent event) {
        UUID playerUUID = event.getPlayer().getUniqueId();

        UUID serverInstanceUUID = UUID.fromString(event.getTarget().getName());
        UUID proxyInstanceUUID = Common.getInstance().getServerUUID();

        PlayerJoinPacket playerJoinPacket = new PlayerJoinPacket(playerUUID, serverInstanceUUID, proxyInstanceUUID);
        Common.getInstance().getRedisClient().publishMessageAsync("playerJoin", playerJoinPacket.serializePacketInfo());
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent event) {
        UUID playerUUID = event.getPlayer().getUniqueId();

        PlayerQuitPacket playerQuitPacket = new PlayerQuitPacket(playerUUID);
        Common.getInstance().getRedisClient().publishMessageAsync("playerQuit", playerQuitPacket.serializePacketInfo());
    }
}
