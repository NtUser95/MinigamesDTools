package com.gmail.borlandlp.minigamesdtools.activepoints.reaction.custom;

import com.gmail.borlandlp.minigamesdtools.activepoints.reaction.Reaction;
import org.bukkit.entity.Entity;

public class ItemGiveReaction extends Reaction {
    @Override
    public void performDamage(Entity reactionInitiator, double damage) {
        System.out.println("ItemGiveReaction->performDamage(" + reactionInitiator.getName() + ", " + damage + ")");
    }

    @Override
    public void performIntersection(Entity reactionInitiator) {
        System.out.println("ItemGiveReaction->performIntersection(" + reactionInitiator.getName() + ")");
    }
}
