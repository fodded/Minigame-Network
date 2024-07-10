package me.fodded.proxyloadbalancer.info.game;

import me.fodded.proxyloadbalancer.info.game.impl.skywars.ranked.RankedSkywarsInstanceFinder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GameInstanceFinderFactory {

    private final Map<String, IGameInstanceFinder<?>> instances = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T extends IGameInstanceFinder<?>> Optional<T> getInstance(String instanceName) {
        return Optional.ofNullable((T) instances.get(instanceName));
    }

    public void registerInstances() {
        registerInstance(new RankedSkywarsInstanceFinder());
    }

    private void registerInstance(IGameInstanceFinder<?> instance) {
        instances.put(instance.getName(), instance);
    }
}
