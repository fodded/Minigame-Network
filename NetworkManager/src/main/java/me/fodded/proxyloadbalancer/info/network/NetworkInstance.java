package me.fodded.proxyloadbalancer.info.network;

import lombok.Getter;
import me.fodded.common.Common;
import org.redisson.api.RMap;

import java.util.UUID;

@Getter
public class NetworkInstance {

    private final RMap<UUID, NetworkPlayer> networkPlayerMap;

    public NetworkInstance() {
        this.networkPlayerMap = Common.getInstance().getRedisClient().getRedissonClient().getMap("networkPlayerMap");
    }

    public void trackPlayer(NetworkPlayer networkPlayer) {
        this.networkPlayerMap.put(networkPlayer.getPlayerUUID(), networkPlayer);
    }

    public void untrackPlayer(NetworkPlayer networkPlayer) {
        this.networkPlayerMap.remove(networkPlayer.getPlayerUUID());
    }
}