package me.fodded.gamemanager.state.game;

import me.fodded.gamemanager.events.handler.GameEventController;

public interface IGameState extends GameEventController {
    void start();
    void end();
}
