package me.fodded.rankedskywars;

import me.fodded.gamemanager.AbstractGame;
import me.fodded.gamemanager.map.info.IGameMapInfo;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class RankedSkywarsGame extends AbstractGame {

    public RankedSkywarsGame(JavaPlugin plugin, IGameMapInfo gameMapInfo) {
        super(plugin, gameMapInfo);
    }

    @Override
    protected void setup() {
        // TODO: to be finished
        World world = Bukkit.getWorld("");
    }

    @Override
    protected void teardown() {

    }

    @Override
    protected void registerGameMechanics() {

    }

    @Override
    public void registerGameStates() {

    }

    @Override
    public void initializePlayerStates() {

    }

    @Override
    public IGameMapInfo getGameMapData() {
        return null;
    }
}
