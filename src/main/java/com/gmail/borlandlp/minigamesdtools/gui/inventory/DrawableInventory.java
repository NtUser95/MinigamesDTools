package com.gmail.borlandlp.minigamesdtools.gui.inventory;

import com.gmail.borlandlp.minigamesdtools.gui.inventory.slots.InventorySlot;

public interface DrawableInventory {
    org.bukkit.inventory.Inventory toBukkitInventory();
    InventorySlot getSlot(int id);
    void setSlot(int id, InventorySlot slot);
    void setRows(int s);
    int getRows();
}
