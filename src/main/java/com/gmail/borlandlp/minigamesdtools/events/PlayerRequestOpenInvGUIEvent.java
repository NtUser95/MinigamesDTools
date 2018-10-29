package com.gmail.borlandlp.minigamesdtools.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerRequestOpenInvGUIEvent extends Event implements Cancellable {
    private boolean isCanceled = false;
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private String pageID;

    public PlayerRequestOpenInvGUIEvent(Player p, String pd) {
        this.player = p;
        this.pageID = pd;
    }

    public Player getPlayer() {
        return player;
    }

    public String getPageID() {
        return pageID;
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
        return "{Event:" + this.getClass().getSimpleName() + " Player" + this.getPlayer() + ", pageID: " + this.pageID + "}";
    }
}
