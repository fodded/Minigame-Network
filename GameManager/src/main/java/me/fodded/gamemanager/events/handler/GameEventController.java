package me.fodded.gamemanager.events.handler;

import org.bukkit.event.Event;

import java.util.function.Consumer;

public interface GameEventController {

    GameEventHandler getEventHandler();
    default <T extends Event> void register(Class<T> eventClass, Consumer<Event> consumer) {
        getEventHandler().registerEvent(eventClass, consumer);
    }
}
