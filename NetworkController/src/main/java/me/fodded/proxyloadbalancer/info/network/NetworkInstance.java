package me.fodded.proxyloadbalancer.info.network;

import lombok.Getter;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import java.util.UUID;

@Getter
public class NetworkInstance {

    private final RMap<UUID, NetworkPlayer> networkPlayerMap;
    private final RedissonClient redissonClient;

    public NetworkInstance(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
        this.networkPlayerMap = redissonClient.getMap("networkPlayerMap");
    }

    public void trackPlayer(NetworkPlayer networkPlayer) {
        this.networkPlayerMap.put(networkPlayer.getPlayerUUID(), networkPlayer);
    }

    public void untrackPlayer(NetworkPlayer networkPlayer) {
        this.networkPlayerMap.remove(networkPlayer.getPlayerUUID());
    }
}