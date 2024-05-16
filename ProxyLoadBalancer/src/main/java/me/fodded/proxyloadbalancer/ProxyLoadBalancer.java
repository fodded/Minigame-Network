package me.fodded.proxyloadbalancer;

import lombok.Getter;
import me.fodded.common.Common;
import me.fodded.common.data.transfer.RedisClient;
import me.fodded.proxyloadbalancer.info.network.NetworkInstance;
import me.fodded.proxyloadbalancer.info.network.listeners.redis.RedisPlayerChangeServer;
import me.fodded.proxyloadbalancer.info.network.listeners.redis.RedisPlayerJoin;
import me.fodded.proxyloadbalancer.info.network.listeners.redis.RedisPlayerQuit;
import me.fodded.proxyloadbalancer.servers.ServerController;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * The Proxy Load Balancer is used as an alternative to redisbungee, to keep track of all the players which are connected to
 * the network. It's also intended to work as a load balancer to move players between best suiting servers
 *
 * The best use case is that it does not let you in a server which is full if you're a regular player, or that it sends you
 * to a lobby which has fewer players in or more depending on the load balancing technique
 */
@Getter
public class ProxyLoadBalancer extends Plugin {

    @Getter
    private static ProxyLoadBalancer instance;

    private NetworkInstance networkInstance;
    private ServerController serverController;

    @Override
    public void onEnable() {
        instance = this;

        this.networkInstance = new NetworkInstance();
        this.serverController = new ServerController();

        initializeRedisListeners();
    }

    private void initializeRedisListeners() {
        RedisClient redisClient = Common.getInstance().getRedisClient();
        redisClient.registerListener(new RedisPlayerJoin(), "playerJoin");
        redisClient.registerListener(new RedisPlayerQuit(), "playerQuit");
        redisClient.registerListener(new RedisPlayerChangeServer(), "playerChangeServer");
    }
}
