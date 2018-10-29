package com.gmail.borlandlp.minigamesdtools.gui.inventory.api;

import com.gmail.borlandlp.minigamesdtools.gui.inventory.DrawableInventory;
import org.bukkit.entity.Player;

public interface InventoryAPI {
    void show(DrawableInventory drawableInventory, Player player);
    DrawableInventory getInventory(Player player);
    boolean isView(Player player);
    void close(Player player);
    void unregister(Player player);
}
