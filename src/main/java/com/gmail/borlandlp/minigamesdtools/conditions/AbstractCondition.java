package com.gmail.borlandlp.minigamesdtools.conditions;

import org.bukkit.entity.Player;

public abstract class AbstractCondition {
    public abstract boolean isValidPlayer(Player p);
    public abstract String getErrorId();
}
