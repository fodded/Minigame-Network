package me.fodded.skywarsgame;

import lombok.Getter;
import me.fodded.common.Common;
import me.fodded.proxyloadbalancer.NetworkController;
import me.fodded.proxyloadbalancer.servers.instances.minigame.MinigameInstance;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        MinigameInstance minigameInstance = new MinigameInstance(Common.getInstance().getServerUUID());
        NetworkController.getInstance().getServerController().addServer(minigameInstance);
    }

    @Override
    public void onDisable() {

    }
}
