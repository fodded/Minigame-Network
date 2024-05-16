package me.fodded.gamemanager;

import me.fodded.gamemanager.map.info.IGameMapInfo;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Game extends AbstractGame {

    public Game(JavaPlugin plugin, IGameMapInfo gameMapInfo) {
        super(plugin, gameMapInfo);
    }
}
