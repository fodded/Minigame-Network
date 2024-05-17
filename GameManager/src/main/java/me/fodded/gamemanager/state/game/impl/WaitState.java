package me.fodded.gamemanager.state.game.impl;

import me.fodded.gamemanager.AbstractGame;
import me.fodded.gamemanager.state.game.AbstractGameState;

public class WaitState extends AbstractGameState {

    private final long delay;

    public WaitState(AbstractGame game, String eventDisplayName, long delay) {
        super(game, eventDisplayName);
        this.delay = delay;
    }

    @Override
    public void start() {
        schedule(() -> game.getGameStateController().switchToNextGameState(), delay);
    }

    @Override
    public void end() {

    }
}
