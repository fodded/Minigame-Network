package me.fodded.gamemanager.mechanics;

import lombok.Getter;
import lombok.Setter;
import me.fodded.gamemanager.AbstractGame;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public abstract class AbstractGameMechanic {

    protected final AbstractGame game;

    @Setter @Getter
    private boolean enabled;

    public AbstractGameMechanic(AbstractGame game) {
        this.game = game;
        this.enabled = enabledByDefault();

        setup();
    }

    public abstract void setup();
    public boolean enabledByDefault() {
        return true;
    }

    public boolean isGameEntity(Entity entity) {
        if (entity == null) {
            return false;
        }

        if (entity instanceof Player) {
            Player player = (Player) entity;
            return game.getGamePlayerTracker().isPlayerInGame(player.getUniqueId());
        }

        return isGameWorld(entity.getWorld());
    }

    public boolean isGameWorld(World world) {
        return world != null && game.getGameWorldId() != null && game.getGameWorldId().equals(world.getUID());
    }
}
