package me.fodded.proxyloadbalancer.info.game;

import me.fodded.proxyloadbalancer.info.network.info.player.NetworkPlayer;
import me.fodded.proxyloadbalancer.servers.instances.ServerInstance;
import me.fodded.proxyloadbalancer.servers.instances.minigame.MinigameInstance;

import java.util.Optional;

public interface IGameInstanceFinder<T extends AbstractGameInstance> {

    /**
     * Looks up the most appropriate game instance to send player to
     * <p>
     * For the vast majority of game instance finders, they work based on
     * 1. look for game instances on this server with the most players queuing (For example 11/12 on the game instance)
     */
    Optional<T> findGameInstance(MinigameInstance<?> minigameInstance);

    /**
     * Looks up the most appropriate server instance to send player to
     * <p>
     * For the vast majority of game instance finders, they work based on
     * 1. look for servers with the least players online (For example 11/100 on the whole server)
     */
    <S extends ServerInstance> S findServerInstance(NetworkPlayer networkPlayer);

    /**
     * Typically used when we want to find an active queue based on a name
     * For example a player types '/play ranked_normal', and it sends him to the queue
     */
    String getName();
}