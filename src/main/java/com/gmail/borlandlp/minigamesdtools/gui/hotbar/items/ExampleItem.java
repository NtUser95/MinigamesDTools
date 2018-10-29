package com.gmail.borlandlp.minigamesdtools.gui.hotbar.items;

import org.bukkit.entity.Player;

public class ExampleItem extends SlotItem {
    @Override
    public boolean use(Player player) {
        System.out.print("use item");
        return true;
    }
}
