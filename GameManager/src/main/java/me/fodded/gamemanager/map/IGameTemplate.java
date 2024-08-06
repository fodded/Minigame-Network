package me.fodded.gamemanager.map;

import me.fodded.gamemanager.IGameActions;
import me.fodded.gamemanager.map.info.IGameMapInfo;

public interface IGameTemplate {
    String getName();

    IGameActions createGame(IGameMapInfo map);
}
