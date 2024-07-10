package me.fodded.proxyloadbalancer.info.network;

import lombok.Getter;
import me.fodded.proxyloadbalancer.info.network.info.player.NetworkPlayer;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import java.util.Optional;
import java.util.UUID;

@Getter
public class NetworkInstance {

    private final RMap<UUID, NetworkPlayer> networkPlayerMap;
    private final RedissonClient redissonClient;

    public NetworkInstance(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
        this.networkPlayerMap = redissonClient.getMap("networkPlayerMap");
    }

    /**
     * @param networkPlayer Player joined the network. It's usually caused by connecting to any proxy
     */
    public void trackPlayer(NetworkPlayer networkPlayer) {
        this.networkPlayerMap.put(networkPlayer.getPlayerUUID(), networkPlayer);
    }

    /**
     * @param networkPlayer Player left the network. It's usually caused by quitting any proxy
     */
    public void untrackPlayer(NetworkPlayer networkPlayer) {
        this.networkPlayerMap.remove(networkPlayer.getPlayerUUID());
    }

    public Optional<NetworkPlayer> getNetworkPlayer(UUID playerUUID) {
        return Optional.ofNullable(networkPlayerMap.get(playerUUID));
    }
}