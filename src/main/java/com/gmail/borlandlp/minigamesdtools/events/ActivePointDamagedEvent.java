package com.gmail.borlandlp.minigamesdtools.events;

import com.gmail.borlandlp.minigamesdtools.activepoints.ActivePoint;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ActivePointDamagedEvent extends Event implements Cancellable {
    private ActivePoint activePoint;
    private Player destroyer;
    private boolean canceled;
    private double damage;
    private static final HandlerList handlers = new HandlerList();

    public ActivePointDamagedEvent(ActivePoint activePoint1, Player destroyer1, double damage1) {
        this.activePoint = activePoint1;
        this.destroyer = destroyer1;
        this.damage = damage1;
    }

    public ActivePoint getActivePoint() {
        return activePoint;
    }

    public Player getDestroyer() {
        return destroyer;
    }

    public double getDamage() {
        return damage;
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
