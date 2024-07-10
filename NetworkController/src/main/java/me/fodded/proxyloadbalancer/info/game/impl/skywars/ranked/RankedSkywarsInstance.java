package me.fodded.proxyloadbalancer.info.game.impl.skywars.ranked;

import lombok.Getter;
import lombok.Setter;
import me.fodded.gamemanager.AbstractGame;
import me.fodded.proxyloadbalancer.info.game.AbstractGameInstance;

@Setter
@Getter
public class RankedSkywarsInstance extends AbstractGameInstance {

    public RankedSkywarsInstance(AbstractGame abstractGame, String instanceServerName) {
        super(abstractGame, instanceServerName);
    }
}
