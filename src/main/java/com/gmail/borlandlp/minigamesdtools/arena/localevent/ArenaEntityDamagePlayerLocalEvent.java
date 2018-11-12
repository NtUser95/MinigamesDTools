package com.gmail.borlandlp.minigamesdtools.arena.localevent;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public class ArenaEntityDamagePlayerLocalEvent extends ArenaEvent implements Cancellable {
    private Entity damager;
    private Player player;
    private double damage;
    private boolean canceled;

    public ArenaEntityDamagePlayerLocalEvent(Entity damager, Player player, double damage) {
        this.damage = damage;
        this.player = player;
        this.damager = damager;
    }

    public Entity getDamager() {
        return damager;
    }

    public double getDamage() {
        return damage;
    }

    public Player getPlayer() {
        return player;
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
