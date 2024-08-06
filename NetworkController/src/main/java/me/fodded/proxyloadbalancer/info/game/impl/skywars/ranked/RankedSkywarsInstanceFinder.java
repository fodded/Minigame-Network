package me.fodded.proxyloadbalancer.info.game.impl.skywars.ranked;

import me.fodded.proxyloadbalancer.NetworkController;
import me.fodded.proxyloadbalancer.info.game.IGameInstanceFinder;
import me.fodded.proxyloadbalancer.info.network.info.player.NetworkPlayer;
import me.fodded.proxyloadbalancer.servers.ServerController;
import me.fodded.proxyloadbalancer.servers.instances.minigame.MinigameInstance;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RankedSkywarsInstanceFinder implements IGameInstanceFinder<RankedSkywarsInstance> {

    @SuppressWarnings("unchecked")
    @Override
    public MinigameInstance<RankedSkywarsInstance> findServerInstance(NetworkPlayer networkPlayer) {
        List<MinigameInstance<RankedSkywarsInstance>> minigameInstances = NetworkController.getInstance().getServerController().getServerInstances(MinigameInstance.class)
                .stream()
                .filter(instance -> instance.getGameInstanceClass().equals(RankedSkywarsInstance.class))
                .map(instance -> (MinigameInstance<RankedSkywarsInstance>) instance)
                .filter(instance -> !instance.getServerName().equalsIgnoreCase(networkPlayer.getServerInstanceName()))
                .sorted(Comparator.comparingInt(instance -> instance.getPlayers().size()))
                .collect(Collectors.toList());

        for (MinigameInstance<RankedSkywarsInstance> minigameInstance : minigameInstances) {
            Optional<RankedSkywarsInstance> foundRankedSkywarsInstance = findGameInstance(minigameInstance);
            if (!foundRankedSkywarsInstance.isPresent()) continue;

            ServerController serverController = NetworkController.getInstance().getServerController();
            String serverInstanceName = foundRankedSkywarsInstance.get().getInstanceServerName();

            return (MinigameInstance<RankedSkywarsInstance>) serverController.getServerInstance(serverInstanceName).get();
        }

        return minigameInstances.get(0);
    }

    @Override
    public Optional<RankedSkywarsInstance> findGameInstance(MinigameInstance<?> minigameInstance) {
        return minigameInstance.getGameInstances(RankedSkywarsInstance.class)
                .stream()
                .filter(instance -> instance.getGame().getGameStateController().getCurrentStateIndex() == 0)
                .sorted(
                        Comparator.comparingInt(instance -> instance.getGame().getGamePlayerTracker().getGamePlayers().size())
                )
                .findFirst();
    }

    @Override
    public String getName() {
        return "ranked_normal";
    }
}
