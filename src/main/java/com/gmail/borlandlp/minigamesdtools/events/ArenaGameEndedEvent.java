package com.gmail.borlandlp.minigamesdtools.events;

import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ArenaGameEndedEvent extends Event implements Cancellable {
    private boolean isCanceled = false;
    private ArenaBase arenaBase;
    private com.gmail.borlandlp.minigamesdtools.arena.Result result;
    private static final HandlerList handlers = new HandlerList();

    public ArenaGameEndedEvent(ArenaBase arenaBase, com.gmail.borlandlp.minigamesdtools.arena.Result result) {
        this.arenaBase = arenaBase;
        this.result = result;
    }

    public ArenaBase getArena()
    {
        return this.arenaBase;
    }

    public com.gmail.borlandlp.minigamesdtools.arena.Result getResult()
    {
        return this.result;
    }

    @Override
    public boolean isCancelled()
    {
        return this.isCanceled;
    }

    @Override
    public void setCancelled(boolean b)
    {
        this.isCanceled = b;
    }

    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
