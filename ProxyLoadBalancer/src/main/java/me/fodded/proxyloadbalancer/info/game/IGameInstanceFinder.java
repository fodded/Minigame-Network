package me.fodded.proxyloadbalancer.info.game;

import java.util.Set;

public interface IGameInstanceFinder<T extends AbstractGameInstance> {
    T findGameInstance(Set<T> gameInstances);
}