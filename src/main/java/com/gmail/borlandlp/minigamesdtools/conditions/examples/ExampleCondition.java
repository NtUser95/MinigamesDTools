package com.gmail.borlandlp.minigamesdtools.conditions.examples;

import com.gmail.borlandlp.minigamesdtools.conditions.AbstractCondition;
import org.bukkit.entity.Player;

public class ExampleCondition extends AbstractCondition {
    @Override
    public boolean isValidPlayer(Player p) {
        return true;
    }

    @Override
    public String getErrorId() {
        return "test_error_msg";
    }
}
