package me.fodded.gamemanager.mechanics.impl;

import me.fodded.gamemanager.AbstractGame;
import me.fodded.gamemanager.mechanics.AbstractGameMechanic;
import org.bukkit.event.block.BlockBreakEvent;

public class GameBreakBlockMechanic extends AbstractGameMechanic {

    public GameBreakBlockMechanic(AbstractGame game) {
        super(game);
    }

    @Override
    public void setup() {
        game.getEventHandler().registerEvent(BlockBreakEvent.class, event -> {
            if (!isEnabled() && isGameEntity(event.getPlayer())) {
                event.setCancelled(true);
            }
        });
    }

    public boolean enabledByDefault() {
        return false;
    }
}