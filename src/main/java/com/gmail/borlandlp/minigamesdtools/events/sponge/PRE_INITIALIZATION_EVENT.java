package com.gmail.borlandlp.minigamesdtools.events.sponge;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/*
* Загрузка всех конфигураций в пул
* */
public class PRE_INITIALIZATION_EVENT extends Event {
    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
