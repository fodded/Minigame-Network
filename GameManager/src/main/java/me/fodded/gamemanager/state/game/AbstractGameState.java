package me.fodded.gamemanager.state.game;

import lombok.Getter;
import me.fodded.gamemanager.AbstractGame;
import me.fodded.gamemanager.events.handler.GameEventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Set;

@Getter
public abstract class AbstractGameState implements Listener, IGameState {

    protected final AbstractGame game;

    protected final Set<Listener> listeners = new HashSet<>();
    protected final Set<BukkitTask> tasks = new HashSet<>();

    private final String eventDisplayName;
    private final GameEventHandler gameEventHandler;

    public AbstractGameState(AbstractGame game, String eventDisplayName) {
        this.game = game;
        this.eventDisplayName = eventDisplayName;
        this.gameEventHandler = new GameEventHandler(game.getPlugin());
    }

    public void startGameState() {
        registerListener(this);
        this.start();
    }

    public void endGameState() {
        listeners.forEach(HandlerList::unregisterAll);
        tasks.forEach(BukkitTask::cancel);
        listeners.clear();
        tasks.clear();
        HandlerList.unregisterAll(gameEventHandler);
        this.end();
    }

    // TODO: remove methods below from here, they don't seem to belong there
    private void registerListener(Listener listener) {
        JavaPlugin plugin = game.getPlugin();
        listeners.add(listener);
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    protected void schedule(Runnable runnable, long delay) {
        JavaPlugin plugin = game.getPlugin();
        BukkitTask task = plugin.getServer().getScheduler().runTaskLater(plugin, runnable, delay);
        tasks.add(task);
    }

    protected void scheduleRepeating(Runnable runnable, long delay, long interval) {
        JavaPlugin plugin = game.getPlugin();
        BukkitTask task = plugin.getServer().getScheduler().runTaskTimer(plugin, runnable, delay, interval);
        tasks.add(task);
    }

    @Override
    public GameEventHandler getEventHandler() {
        return gameEventHandler;
    }
}
