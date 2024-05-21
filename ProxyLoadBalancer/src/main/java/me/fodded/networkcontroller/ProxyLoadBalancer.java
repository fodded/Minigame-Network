package me.fodded.networkcontroller;

import lombok.Getter;
import me.fodded.common.Common;
import me.fodded.common.data.statistics.transfer.RedisClient;
import me.fodded.networkcontroller.listeners.bungeecord.PlayerConnectListener;
import me.fodded.networkcontroller.listeners.redis.RedisPlayerChangeServer;
import me.fodded.networkcontroller.listeners.redis.RedisPlayerJoin;
import me.fodded.networkcontroller.listeners.redis.RedisPlayerQuit;
import me.fodded.proxyloadbalancer.NetworkController;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

/**
 * ProxyLoadBalancer was invented to handle multiple proxy connections and synchronize them.
 *
 * Generally saying, ProxyLoadBalancer is a bare-bone API and ProxyLoadBalancer is using this API to
 * manage different proxies, to make it looks like one big network
 */
@Getter
public class ProxyLoadBalancer extends Plugin {

    @Getter
    private static ProxyLoadBalancer instance;

    /**
     * We need to initialzi
     */
    @Override
    public void onEnable() {
        instance = this;
        RedisClient redisClient = Common.getInstance().getRedisClient();

        initializeRedisListeners(redisClient);
        initializeProxyListeners();

        new NetworkController(redisClient.getRedissonClient());
    }

    private void initializeProxyListeners() {
        PluginManager pluginManager = getProxy().getPluginManager();
        pluginManager.registerListener(this, new PlayerConnectListener());
    }

    private void initializeRedisListeners(RedisClient redisClient) {
        redisClient.registerListener(new RedisPlayerJoin(), "playerJoin");
        redisClient.registerListener(new RedisPlayerQuit(), "playerQuit");
        redisClient.registerListener(new RedisPlayerChangeServer(), "playerChangeServer");
    }

    @Override
    public void onDisable() {
        //TODO: remove server from tracker
    }
}
