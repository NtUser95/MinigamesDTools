package com.gmail.borlandlp.minigamesdtools.test.gui.inventory.slots;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.gui.inventory.slots.AbstractSlot;
import org.bukkit.entity.Player;

public class ExampleInventorySlot extends AbstractSlot {
    @Override
    public void performClick(Player player) {
        Debug.print(Debug.LEVEL.NOTICE, "Perform click for " + player.getName());
    }
}
