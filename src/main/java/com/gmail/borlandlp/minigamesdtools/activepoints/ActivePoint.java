package com.gmail.borlandlp.minigamesdtools.activepoints;

import com.gmail.borlandlp.minigamesdtools.activepoints.behaviors.Behavior;
import com.gmail.borlandlp.minigamesdtools.activepoints.reaction.Reaction;
import com.gmail.borlandlp.minigamesdtools.activepoints.reaction.ReactionReason;
import com.gmail.borlandlp.minigamesdtools.events.ActivePointDamagedEvent;
import com.gmail.borlandlp.minigamesdtools.events.ActivePointDestroyedEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ActivePoint {
    private String name;
    private ActivePointController activePointController;
    private boolean damageable;
    private boolean performDamage;
    private boolean performEntityIntersection;
    private boolean performInteraction;
    private Location location;
    private boolean spawned = false;
    private List<Behavior> behavior;
    private Map<ReactionReason, List<Reaction>> reactions = new HashMap<>();

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

    public boolean isPerformInteraction() {
        return performInteraction;
    }

    public void setPerformInteraction(boolean performInteraction) {
        this.performInteraction = performInteraction;
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

    @Deprecated
    public void setReactions(Map<ReactionReason, List<Reaction>> reactions) {
        this.reactions = reactions;
    }

    public void setReaction(ReactionReason reason, List<Reaction> reactions) {
        this.reactions.put(reason, reactions);
    }

    public boolean isPerformDamage() {
        return performDamage;
    }

    public void setPerformDamage(boolean performDamage) {
        this.performDamage = performDamage;
    }

    public void performDamage(Entity entity, double damage) {
        if(!this.isPerformDamage()) {
            return;
        }

        Cancellable event = null;
        if(this.getHealth() <= damage) {
            event = new ActivePointDestroyedEvent(this, (Player) entity);
        } else {
            event = new ActivePointDamagedEvent(this, (Player) entity, damage);
        }

        Bukkit.getServer().getPluginManager().callEvent((Event) event);

        if(event.isCancelled()) {
            return;
        } else if(event instanceof ActivePointDestroyedEvent) {
            this.onDestroy();
            this.getActivePointController().deactivatePoint(this);
        }

        for(Reaction reaction : this.getReactions().get(ReactionReason.DAMAGE)) {
            reaction.performDamage(entity, damage);
        }
    }

    public void performIntersect(Entity entity) {
        if(!this.isPerformEntityIntersection()) {
            return;
        }

        for(Reaction reaction : this.getReactions().get(ReactionReason.INTERSECT)) {
            reaction.performIntersection(entity);
        }
    }

    public void performInteract(Entity entity) {
        if(!this.isPerformInteraction()) {
            return;
        }

        for(Reaction reaction : this.getReactions().get(ReactionReason.INTERACT)) {
            reaction.performInteraction(entity);
        }
    }

    // subclasses can override, if the point has been destroyed by player
    public void onDestroy() {

    }

    public abstract void spawn();
    public abstract void despawn();
    public abstract double getHealth();

    public String toString() {
        return new StringBuilder().append("{ActivePoint")
                .append(" name=").append(this.name)
                .append(" type=").append(getClass().getSimpleName())
                .append(" hp=").append(this.getHealth())
                .append(" location=").append(this.location)
                .append(" performDamage=").append(this.performDamage)
                .append(" performInteract=").append(this.performInteraction)
                .append(" performIntersection=").append(this.performEntityIntersection)
                .append("}").toString();
    }
}
