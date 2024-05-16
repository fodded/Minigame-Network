package me.fodded.gamemanager.mechanics.impl;

import me.fodded.gamemanager.AbstractGame;
import me.fodded.gamemanager.mechanics.AbstractGameMechanic;
import org.bukkit.event.block.BlockPlaceEvent;

public class GamePlaceBlockMechanic extends AbstractGameMechanic {

    public GamePlaceBlockMechanic(AbstractGame game) {
        super(game);
    }

    @Override
    public void setup() {
        game.getEventHandler().registerEvent(BlockPlaceEvent.class, event -> {
            if (!isEnabled() && isGameEntity(event.getPlayer())) {
                event.setCancelled(true);
            }
        });
    }

    public boolean enabledByDefault() {
        return false;
    }
}