package com.gmail.borlandlp.minigamesdtools.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ArenaRequestSpectatePlayer2Player extends Event implements Cancellable {
    private boolean isCanceled = false;
    private static final HandlerList handlers = new HandlerList();
    private Player playerInvoker;
    private Player playerTarget;

    public ArenaRequestSpectatePlayer2Player(Player pi, Player pt) {
        this.playerInvoker = pi;
        this.playerTarget = pt;
    }

    public Player getPlayerInvoker() {
        return playerInvoker;
    }

    public Player getPlayerTarget() {
        return playerTarget;
    }

    @Override
    public boolean isCancelled() {
        return this.isCanceled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCanceled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public String toString() {
        return "{Event:" + this.getClass().getSimpleName() + " PlayerInvoker:" + this.getPlayerInvoker() + ", playerTarget: " + this.getPlayerTarget() + "}";
    }
}
