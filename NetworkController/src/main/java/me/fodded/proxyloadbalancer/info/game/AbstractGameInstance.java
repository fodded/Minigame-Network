package me.fodded.proxyloadbalancer.info.game;

import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class AbstractGameInstance {

    private final UUID gameId;

    public AbstractGameInstance(UUID gameId) {
        this.gameId = gameId;
    }
}
