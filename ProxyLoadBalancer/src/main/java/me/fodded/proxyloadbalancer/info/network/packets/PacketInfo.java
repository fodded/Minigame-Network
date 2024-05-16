package me.fodded.proxyloadbalancer.info.network.packets;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import java.lang.reflect.Type;

/**
 * The packets are sent from the proxy side to the proxy load balancer to handle all connected players to the network and where they are right now
 */
public abstract class PacketInfo {

    @Expose
    private static Gson GSON = new Gson();

    public <T> PacketInfo deserializePacketInfo(String serializedString, T packetInfoType) {
        return GSON.fromJson(serializedString, (Type) packetInfoType);
    }

    public String serializePacketInfo() {
        return GSON.toJson(this);
    }
}