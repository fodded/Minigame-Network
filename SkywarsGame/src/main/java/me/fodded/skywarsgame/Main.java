package me.fodded.skywarsgame;

import lombok.Getter;
import me.fodded.common.ServerBuilder;
import me.fodded.common.ServerCommon;
import me.fodded.common.data.metrics.storage.InfluxStorage;
import me.fodded.metrics.impl.PlayersOnlineMetrics;
import me.fodded.metrics.impl.TPSMetrics;
import me.fodded.proxyloadbalancer.NetworkController;
import me.fodded.proxyloadbalancer.info.game.impl.skywars.ranked.RankedSkywarsInstance;
import me.fodded.proxyloadbalancer.servers.instances.minigame.MinigameInstance;
import me.fodded.skywarsgame.data.storages.SkywarsStorageController;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.InetSocketAddress;

@Getter
public class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    private ServerCommon serverCommon;
    private MinigameInstance<RankedSkywarsInstance> minigameInstance;

    @Override
    public void onEnable() {
        instance = this;

        InfluxStorage influxStorage = new InfluxStorage(
                System.getenv("INFLUX_URL"),
                System.getenv("INFLUX_TOKEN"),
                System.getenv("INFLUX_ORG"),
                System.getenv("INFLUX_BUCKET")
        );

        serverCommon = new ServerBuilder()
                .initialize(System.getenv("SERVER_INSTANCE_NAME"))
                .initializeDataStorage(new SkywarsStorageController())
                .initializeRedis(
                        System.getenv("REDIS_CONNECTION_ADDRESS"),
                        Integer.parseInt(System.getenv("REDIS_CONNECTION_PORT"))
                )
                .initializeMetrics(influxStorage, new TPSMetrics(), new PlayersOnlineMetrics())
                .build();

        Server server = this.getServer();
        InetSocketAddress serverAddress = new InetSocketAddress(server.getIp(), server.getPort());

        minigameInstance = new MinigameInstance<>(serverCommon.getServerName(), serverAddress, RankedSkywarsInstance.class);
        NetworkController.getInstance().getServerController().addServerInstance(minigameInstance);
    }

    @Override
    public void onDisable() {
        NetworkController.getInstance().getServerController().removeServerInstance(System.getenv("SERVER_INSTANCE_NAME"));
    }
}
