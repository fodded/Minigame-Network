package me.fodded.gamemanager;

import me.fodded.gamemanager.map.info.IGameMapInfo;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface IGame {
    UUID getGameId();

    void removePlayer(Player player);
    void addPlayer(Player player);

    void start();
    void end();

    void registerGameStates();
    void initializePlayerStates();

    IGameMapInfo getGameMapData();
}
