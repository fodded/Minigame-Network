package me.fodded.proxyloadbalancer.servers.instances.minigame;

import lombok.Getter;
import me.fodded.proxyloadbalancer.info.game.AbstractGameInstance;
import me.fodded.proxyloadbalancer.servers.instances.ServerInstance;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MinigameInstance<T extends AbstractGameInstance> extends ServerInstance {

    private final List<T> gameInstances = new ArrayList<>();

    @Getter
    private final Class<T> gameInstanceClass;

    public MinigameInstance(String serverName, InetSocketAddress serverAddress, Class<T> gameInstanceClass) {
        super(serverName, serverAddress);
        this.gameInstanceClass = gameInstanceClass;
    }

    public void addGameInstance(T gameInstance) {
        gameInstances.add(gameInstance);
    }

    public void removeGameInstance(T gameInstance) {
        gameInstances.remove(gameInstance);
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractGameInstance> List<T> getGameInstances(Class<T> gameInstanceClass) {
        return (List<T>) Collections.unmodifiableList(gameInstances)
                .stream()
                .filter(serverInstance -> serverInstance.getClass().equals(gameInstanceClass))
                .collect(Collectors.toList());
    }

    @Override
    public String getServerTypeName() {
        return "minigame";
    }
}