package me.fodded.gamemanager.state.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.*;

public class PlayerStateTracker implements Listener {

    private final Map<UUID, List<String>> playerStates = new HashMap<>();
    private final PlayerStateRegistry registry;

    public PlayerStateTracker(PlayerStateRegistry registry) {
        this.registry = registry;
    }

    public void addState(Player player, AbstractPlayerState state) {
        UUID playerId = player.getUniqueId();
        String stateName = state.getName();

        List<String> states = playerStates.computeIfAbsent(playerId, irrelevant -> new ArrayList<>());
        if (states.contains(stateName)) {
            return;
        }

        states.add(stateName);
        state.apply(player);
    }

    public void addState(Player player, Class<? extends AbstractPlayerState> playerStateClass) {
        addState(player, registry.getState(playerStateClass));
    }

    public void removeState(Player player, AbstractPlayerState state) {
        UUID playerId = player.getUniqueId();
        String stateName = state.getName();

        List<String> states = playerStates.computeIfAbsent(playerId, irrelevant -> new ArrayList<>());
        states.remove(stateName);
    }

    public void removeState(Player player, Class<? extends AbstractPlayerState> playerStateClass) {
        removeState(player, registry.getState(playerStateClass));
    }

    public boolean hasState(Player player, String state) {
        return playerStates.getOrDefault(player.getUniqueId(), Collections.EMPTY_LIST).contains(state);
    }

    public Set<UUID> getPlayersFromState(AbstractPlayerState state) {
        Set<UUID> playersFromState = new HashSet<>();
        playerStates.forEach((uuid, states) -> {
            if (states.contains(state.getName())) {
                playersFromState.add(uuid);
            }
        });
        return playersFromState;
    }

    public Set<UUID> getPlayersFromState(Class<? extends AbstractPlayerState> playerStateClass) {
        return getPlayersFromState(registry.getState(playerStateClass));
    }

    public Set<Player> getPresentPlayers() {
        Set<Player> players = new HashSet<>();
        playerStates.forEach((uuid, states) -> {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                players.add(player);
            }
        });
        return players;
    }
}