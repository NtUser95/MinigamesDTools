package com.gmail.borlandlp.minigamesdtools.activepoints.reaction.custom;

import com.gmail.borlandlp.minigamesdtools.Debug;
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
        Debug.print(Debug.LEVEL.NOTICE, "TeamIncrementWinTicketsReaction->performDamage(" + reactionInitiator.getName() + ", " + damage + ")");
    }

    @Override
    public void performIntersection(Entity reactionInitiator) {
        Debug.print(Debug.LEVEL.NOTICE, "TeamIncrementWinTicketsReaction->performIntersection(" + reactionInitiator.getName() + ")");
    }
}
