package me.fodded.networkcontroller;

import lombok.Getter;
import me.fodded.common.ServerBuilder;
import me.fodded.common.ServerCommon;
import me.fodded.common.data.metrics.storage.InfluxStorage;
import me.fodded.common.data.statistics.transfer.RedisClient;
import me.fodded.networkcontroller.data.storages.ProxyStorageController;
import me.fodded.networkcontroller.listeners.bungeecord.PlayerConnectListener;
import me.fodded.networkcontroller.listeners.redis.RedisPlayerChangeServer;
import me.fodded.networkcontroller.listeners.redis.RedisPlayerJoin;
import me.fodded.networkcontroller.listeners.redis.RedisPlayerQuit;
import me.fodded.proxyloadbalancer.NetworkController;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

/**
 * ProxyLoadBalancer was invented to handle multiple proxy connections and synchronize them.
 * <p>
 * Generally saying, NetworkController is a bare-bone API and ProxyLoadBalancer is using this API to
 * manage different proxies, to make it looks like one big network
 */
@Getter
public class ProxyLoadBalancer extends Plugin {

    @Getter
    private static ProxyLoadBalancer instance;

    private ServerCommon serverCommon;

    @Override
    public void onEnable() {
        instance = this;

        InfluxStorage influxStorage = new InfluxStorage(
                System.getenv("INFLUX_URL"),
                System.getenv("INFLUX_TOKEN"),
                System.getenv("INFLUX_ORG"),
                System.getenv("INFLUX_BUCKET")
        );

        // TODO: initialize special metrics for proxy
        serverCommon = new ServerBuilder()
                .initialize(System.getenv("SERVER_INSTANCE_NAME"))
                .initializeDataStorage(new ProxyStorageController())
                .initializeRedis(
                        System.getenv("REDIS_CONNECTION_ADDRESS"),
                        Integer.parseInt(System.getenv("REDIS_CONNECTION_PORT"))
                )
                .initializeMetrics(influxStorage)
                .build();

        RedisClient redisClient = serverCommon.getRedisClient();

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
        NetworkController.getInstance().getServerController().removeServerInstance("proxy-1");
    }
}
