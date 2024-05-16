package me.fodded.gamemanager.tracker;

import me.fodded.gamemanager.AbstractGame;
import me.fodded.gamemanager.events.impl.GamePlayerAddEvent;
import me.fodded.gamemanager.events.impl.GamePlayerRemoveEvent;
import org.bukkit.Bukkit;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GamePlayerTracker {

    private final AbstractGame game;
    private final Set<UUID> gamePlayers = new HashSet<>();

    public GamePlayerTracker(AbstractGame game) {
        this.game = game;
    }

    public void addPlayer(UUID playerUUID) {
        this.gamePlayers.add(playerUUID);
        GamePlayerAddEvent event = new GamePlayerAddEvent(playerUUID, game);
        Bukkit.getPluginManager().callEvent(event);
    }

    public void removePlayer(UUID playerUUID) {
        this.gamePlayers.remove(playerUUID);
        GamePlayerRemoveEvent event = new GamePlayerRemoveEvent(playerUUID, game);
        Bukkit.getPluginManager().callEvent(event);
    }

    public boolean isPlayerInGame(UUID playerUniqueId) {
        return this.gamePlayers.contains(playerUniqueId);
    }
}
