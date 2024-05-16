package me.fodded.gamemanager.map.info;


import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

@Getter
public abstract class AbstractGameInfoCache extends BukkitRunnable {

    private final Set<IGameMapInfo> cachedGameMapInfo = new HashSet<>();

    public void addCachedGameMapInfo(IGameMapInfo gameTemplateInfo) {
        this.cachedGameMapInfo.add(gameTemplateInfo);
    }

    public void clearCachedGameMapsInfo() {
        this.cachedGameMapInfo.clear();
    }
}