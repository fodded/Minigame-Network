package me.fodded.gamemanager.events.impl;

import lombok.Getter;
import me.fodded.gamemanager.AbstractGame;

import java.util.UUID;

@Getter
public class GamePlayerAddEvent extends GameEvent {

    private final UUID playerUUID;

    public GamePlayerAddEvent(UUID playerUUID, AbstractGame game) {
        super(game);
        this.playerUUID = playerUUID;
    }
}
