package me.fodded.skywarsgame;

import lombok.Getter;
import me.fodded.common.Common;
import me.fodded.common.data.metrics.storage.InfluxStorage;
import me.fodded.proxyloadbalancer.NetworkController;
import me.fodded.proxyloadbalancer.servers.instances.minigame.MinigameInstance;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        InfluxStorage influxStorage = new InfluxStorage("", "", "", "");
        Common common = Common.getInstance()
                .initialize("skywarsgame-server-1")
                .initializeRedis("localhost", 6379)
                .initializeMetrics(influxStorage);

        MinigameInstance minigameInstance = new MinigameInstance(common.getServerName());
        NetworkController.getInstance().getServerController().addServer(minigameInstance);
    }

    @Override
    public void onDisable() {

    }
}
