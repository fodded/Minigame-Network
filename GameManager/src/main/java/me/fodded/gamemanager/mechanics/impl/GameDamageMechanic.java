package me.fodded.gamemanager.mechanics.impl;

import me.fodded.gamemanager.AbstractGame;
import me.fodded.gamemanager.mechanics.AbstractGameMechanic;
import org.bukkit.event.entity.EntityDamageEvent;

public class GameDamageMechanic extends AbstractGameMechanic {

    public GameDamageMechanic(AbstractGame game) {
        super(game);
    }

    @Override
    public void setup() {
        game.getEventHandler().registerEvent(EntityDamageEvent.class, event -> {
            if (!isEnabled() && isGameEntity(event.getEntity())) {
                event.setCancelled(true);
            }
        });
    }

    public boolean enabledByDefault() {
        return false;
    }
}
