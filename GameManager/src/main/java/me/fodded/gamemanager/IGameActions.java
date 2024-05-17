package me.fodded.gamemanager;

import me.fodded.gamemanager.map.info.IGameMapInfo;

import java.util.UUID;

public interface IGameActions {
    UUID getGameId();

    void start();
    void end();

    void registerGameStates();
    void initializePlayerStates();

    <T extends IGameMapInfo> T getGameMapData();
}
