package me.fodded.skywarsgame;

import lombok.Getter;
import me.fodded.common.ServerBuilder;
import me.fodded.common.ServerCommon;
import me.fodded.common.data.metrics.storage.InfluxStorage;
import me.fodded.proxyloadbalancer.NetworkController;
import me.fodded.proxyloadbalancer.servers.instances.minigame.MinigameInstance;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.InetSocketAddress;

@Getter
public class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    private ServerCommon serverCommon;

    @Override
    public void onEnable() {
        instance = this;

        // TODO: initialize config and then retrieve information from there to put actual data in the influxStorage and ServerCommon
        InfluxStorage influxStorage = new InfluxStorage("", "", "", "");
        SkywarsStorageController skywarsStorageController = new SkywarsStorageController();

        this.serverCommon = new ServerBuilder()
                .initialize("skywarsgame-server-1")
                .initializeDataStorage(skywarsStorageController)
                .initializeRedis("localhost", 6379)
                .initializeMetrics(influxStorage)
                .build();

        Server server = this.getServer();
        InetSocketAddress serverAddress = new InetSocketAddress(server.getIp(), server.getPort());

        MinigameInstance minigameInstance = new MinigameInstance(serverCommon.getServerName(), serverAddress);
        NetworkController.getInstance().getServerController().addServer(minigameInstance);
    }

    @Override
    public void onDisable() {
        NetworkController.getInstance().getServerController().removeServer("skywarsgame-server-1");
    }
}
