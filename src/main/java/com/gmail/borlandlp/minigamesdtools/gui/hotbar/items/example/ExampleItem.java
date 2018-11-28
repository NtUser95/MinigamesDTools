package com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.example;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.SlotItem;
import org.bukkit.entity.Player;

public class ExampleItem extends SlotItem {
    @Override
    public boolean use(Player player) {
        Debug.print(Debug.LEVEL.NOTICE, "use item");
        return true;
    }
}
