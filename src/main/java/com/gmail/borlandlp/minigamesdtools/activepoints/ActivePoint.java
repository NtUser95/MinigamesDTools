package com.gmail.borlandlp.minigamesdtools.activepoints;

import com.gmail.borlandlp.minigamesdtools.activepoints.behaviors.Behavior;
import com.gmail.borlandlp.minigamesdtools.activepoints.reaction.Reaction;
import com.gmail.borlandlp.minigamesdtools.activepoints.reaction.ReactionReason;
import com.gmail.borlandlp.minigamesdtools.events.ActivePointDamagedLocalEvent;
import com.gmail.borlandlp.minigamesdtools.events.ActivePointDestroyedLocalEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public abstract class ActivePoint {
    private String name;
    private ActivePointController activePointController;
    private boolean damageable;
    private boolean performDamage;
    private boolean performEntityIntersection;
    private Location location;
    private boolean spawned = false;
    private List<Behavior> behavior;
    private Map<ReactionReason, List<Reaction>> reactions = new HashMap<>();
    private Map<LivingEntity, Long> damageCooldowns = new Hashtable<>();
    private Map<LivingEntity, Long> intersectionCooldowns = new Hashtable<>();
    private long damageCdNanoSeconds = 500000; // 0.5 sec
    private long intersectionCdNanoSeconds = 1000000; // 1 sec

    public List<Behavior> getBehaviors() {
        return behavior;
    }

    public void setBehaviors(List<Behavior> behavior) {
        this.behavior = behavior;
    }

    public ActivePointController getActivePointController() {
        return activePointController;
    }

    public void setActivePointController(ActivePointController activePointController) {
        this.activePointController = activePointController;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSpawned() {
        return spawned;
    }

    public void setSpawned(boolean spawned) {
        this.spawned = spawned;
    }

    public boolean isDamageable() {
        return damageable;
    }

    public void setDamageable(boolean damageable) {
        this.damageable = damageable;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isPerformEntityIntersection() {
        return performEntityIntersection;
    }

    public void setPerformEntityIntersection(boolean performEntityIntersection) {
        this.performEntityIntersection = performEntityIntersection;
    }

    public Map<ReactionReason, List<Reaction>> getReactions() {
        return reactions;
    }

    public List<Reaction> getReactionsByReason(ReactionReason reactionReason) {
        return this.getReactions().get(reactionReason);
    }

    public void setReactions(Map<ReactionReason, List<Reaction>> reactions) {
        this.reactions = reactions;
    }

    public boolean isPerformDamage() {
        return performDamage;
    }

    public void setPerformDamage(boolean performDamage) {
        this.performDamage = performDamage;
    }

    public void performDamage(LivingEntity entity, double damage) {
        if(!this.isPerformDamage()) {
            return;
        }

        if(this.damageCooldowns.containsKey(entity) && (System.nanoTime() - this.damageCooldowns.get(entity)) <= this.damageCdNanoSeconds) {
            return;
        } else {
            this.damageCooldowns.put(entity, System.nanoTime());
        }

        Cancellable event = null;
        if(this.getHealth() <= damage) {
            event = new ActivePointDestroyedLocalEvent(this, (Player) entity);
        } else {
            event = new ActivePointDamagedLocalEvent(this, (Player) entity, damage);
        }

        Bukkit.getServer().getPluginManager().callEvent((Event) event);

        if(event.isCancelled()) {
            return;
        } else if(event instanceof ActivePointDestroyedLocalEvent) {
            this.onDestroy();
            this.getActivePointController().deactivatePoint(this);
        }

        for(Reaction reaction : this.getReactions().get(ReactionReason.DAMAGE)) {
            reaction.performDamage(entity, damage);
        }
    }

    public void performIntersect(LivingEntity entity) {
        if(!this.isPerformEntityIntersection()) {
            return;
        }

        if(this.intersectionCooldowns.containsKey(entity) && (System.nanoTime() - this.intersectionCooldowns.get(entity)) <= this.intersectionCdNanoSeconds) {
            return;
        } else {
            this.intersectionCooldowns.put(entity, System.nanoTime());
        }

        for(Reaction reaction : this.getReactions().get(ReactionReason.INTERSECT)) {
            reaction.performIntersection(entity);
        }
    }

    // subclasses can override, if the point has been destroyed by player
    public void onDestroy() {

    }

    public abstract void spawn();
    public abstract void despawn();
    public abstract double getHealth();
}
