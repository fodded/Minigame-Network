package me.fodded.gamemanager.map;

import me.fodded.gamemanager.IGame;
import me.fodded.gamemanager.map.info.IGameMapInfo;

public interface IGameTemplate {
    String getName();
    IGame createGame(IGameMapInfo map);
}
