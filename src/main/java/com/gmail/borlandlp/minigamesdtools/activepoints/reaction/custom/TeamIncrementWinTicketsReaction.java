package com.gmail.borlandlp.minigamesdtools.activepoints.reaction.custom;

import com.gmail.borlandlp.minigamesdtools.activepoints.reaction.Reaction;
import org.bukkit.entity.Entity;

public class TeamIncrementWinTicketsReaction extends Reaction {
    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public void performDamage(Entity reactionInitiator, double damage) {
        System.out.println("TeamIncrementWinTicketsReaction->performDamage(" + reactionInitiator.getName() + ", " + damage + ")");
    }

    @Override
    public void performIntersection(Entity reactionInitiator) {
        System.out.println("TeamIncrementWinTicketsReaction->performIntersection(" + reactionInitiator.getName() + ")");
    }
}
