package me.fodded.skywarsgame.game.ranked;

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
        getGameStateController().registerGameState(new );
    }

    @Override
    public void initializePlayerStates() {

    }
}
