package com.gmail.borlandlp.minigamesdtools.arena.localevent;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

@ArenaEventHandler
public class ArenaPlayerKilledLocalEvent extends ArenaEvent implements Cancellable {
    private Player player;
    private Entity killer;
    private boolean canceled;

    public ArenaPlayerKilledLocalEvent(Player player, Entity killer) {
        this.player = player;
        this.killer = killer;
    }

    public Player getPlayer() {
        return player;
    }

    public Entity getKiller() {
        return killer;
    }

    public String toString() {
        return "{Event:" + this.getClass().getSimpleName() + " Player" + this.getPlayer() + " Killer:" + this.getKiller() +"}";
    }

    @Override
    public boolean isCancelled() {
        return this.canceled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.canceled = b;
    }
}
