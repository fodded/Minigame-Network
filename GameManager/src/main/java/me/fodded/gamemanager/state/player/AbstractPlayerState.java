package me.fodded.gamemanager.state.player;

import lombok.Getter;
import me.fodded.gamemanager.AbstractGame;
import org.bukkit.entity.Player;

@Getter
public abstract class AbstractPlayerState {

    private final PlayerStateTracker tracker;
    private final AbstractGame abstractGame;

    public AbstractPlayerState(PlayerStateTracker tracker, AbstractGame abstractGame) {
        this.tracker = tracker;
        this.abstractGame = abstractGame;
    }

    public abstract String getName();
    public abstract void apply(Player player);

    public boolean isAppliedTo(Player player) {
        return tracker.hasState(player, getName());
    }
}