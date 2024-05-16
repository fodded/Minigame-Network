package me.fodded.gamemanager.mechanics.impl;

import me.fodded.gamemanager.AbstractGame;
import me.fodded.gamemanager.mechanics.AbstractGameMechanic;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

public class GameOpenChestMechanic extends AbstractGameMechanic {

    public GameOpenChestMechanic(AbstractGame game) {
        super(game);
    }

    @Override
    public void setup() {
        game.getEventHandler().registerEvent(PlayerInteractEvent.class, event -> {
            if (!isEnabled() && isGameEntity(event.getPlayer())) {
                if(event.getClickedBlock().getType() != Material.CHEST) {
                    return;
                }

                event.setCancelled(true);
            }
        });
    }

    public boolean enabledByDefault() {
        return false;
    }
}
