package me.fodded.gamemanager.events.handler;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

public class GameEventHandler implements Listener {

    private final JavaPlugin plugin;
    public GameEventHandler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public <T extends Event> void registerEvent(Class<T> eventClass, Consumer<Event> action) {
        Bukkit.getPluginManager().registerEvent(eventClass, this, EventPriority.NORMAL, (listener, event) -> {
            if (!eventClass.isInstance(event)) {
                return;
            }
            action.accept(event);
        }, plugin, true);
    }
}
