package me.fodded.proxyloadbalancer.info.game;

import lombok.Getter;
import me.fodded.gamemanager.AbstractGame;

@Getter
public abstract class AbstractGameInstance {

    private final AbstractGame game;
    private final String instanceServerName;

    public AbstractGameInstance(AbstractGame game, String instanceServerName) {
        this.game = game;
        this.instanceServerName = instanceServerName;
    }
}
