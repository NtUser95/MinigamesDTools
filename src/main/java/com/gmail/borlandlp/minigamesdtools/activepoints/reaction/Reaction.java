package com.gmail.borlandlp.minigamesdtools.activepoints.reaction;

import com.gmail.borlandlp.minigamesdtools.activepoints.ActivePoint;
import org.bukkit.entity.Entity;

public abstract class Reaction {
    protected ActivePoint activePoint;

    public void setActivePoint(ActivePoint activePoint) {
        this.activePoint = activePoint;
    }

    public ActivePoint getActivePoint() {
        return activePoint;
    }

    public abstract void performDamage(Entity reactionInitiator, double damage);
    public abstract void performIntersection(Entity reactionInitiator);
}
