package me.fodded.proxyloadbalancer;

import lombok.Getter;
import me.fodded.proxyloadbalancer.info.network.NetworkInstance;
import me.fodded.proxyloadbalancer.servers.ServerController;
import org.redisson.api.RedissonClient;

/**
 * The Proxy Load Balancer is used as an alternative to redisbungee, to keep track of all the players which are connected to
 * the proxyloadbalancer. It's also intended to work as a load balancer to move players between best suiting servers
 *
 * The best use case is that it does not let you in a server which is full if you're a regular player, or that it sends you
 * to a lobby which has fewer players in or more depending on the load balancing technique
 */

// TODO: create a separate class which will be extending Plugin
@Getter
public class NetworkController {

    @Getter
    private static NetworkController instance;

    private final NetworkInstance networkInstance;
    private final ServerController serverController;

    public NetworkController(RedissonClient redissonClient) {
        instance = this;

        this.networkInstance = new NetworkInstance(redissonClient);
        this.serverController = new ServerController(redissonClient);
    }
}