package me.fodded.gamemanager.state.player;

import java.util.HashMap;
import java.util.Map;

public class PlayerStateRegistry {

    private final Map<Class<? extends AbstractPlayerState>, AbstractPlayerState> states = new HashMap<>();

    public void registerState(AbstractPlayerState state) {
        states.put(state.getClass(), state);
    }

    public AbstractPlayerState getState(Class<? extends AbstractPlayerState> playerClass) {
        return states.get(playerClass);
    }
}
