package com.gmail.borlandlp.minigamesdtools.events;

import com.gmail.borlandlp.minigamesdtools.activepoints.ActivePoint;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ActivePointDestroyedLocalEvent extends Event implements Cancellable {
    private ActivePoint activePoint;
    private Player destroyer;
    private boolean canceled;
    private static final HandlerList handlers = new HandlerList();

    public ActivePointDestroyedLocalEvent(ActivePoint activePoint1, Player destroyer1) {
        this.activePoint = activePoint1;
        this.destroyer = destroyer1;
    }

    public ActivePoint getActivePoint() {
        return activePoint;
    }

    public Player getDestroyer() {
        return destroyer;
    }

    @Override
    public boolean isCancelled() {
        return this.canceled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.canceled = b;
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
