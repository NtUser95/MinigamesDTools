package com.gmail.borlandlp.minigamesdtools.arena.localevent;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public class ArenaPlayerDamagedLocalEvent extends ArenaEvent implements Cancellable {
    private Player player;
    private double damage;
    private boolean canceled;

    public ArenaPlayerDamagedLocalEvent(Player p, double d) {
        this.player = p;
        this.damage = d;
    }

    public Player getPlayer() {
        return player;
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
}
