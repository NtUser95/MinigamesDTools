package com.gmail.borlandlp.minigamesdtools.gui.inventory;

import com.gmail.borlandlp.minigamesdtools.gui.inventory.slots.InventorySlot;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Hashtable;
import java.util.Map;

public class DefaultViewInventory implements DrawableInventory {
    private Map<Integer, InventorySlot> slots = new Hashtable<>();
    private int mForceRows = 0;

    @Override
    public Inventory toBukkitInventory() {
        int invSlots = (mForceRows == 0) ? (int)(Math.ceil(this.slots.size() / 9D) * 9D) : mForceRows * 9;
        Inventory bukkitInventory = Bukkit.getServer().createInventory(null, invSlots, "test title");

        for (Integer Id : this.slots.keySet()) {
            bukkitInventory.setItem(Id, this.slots.get(Id).build());
        }

        ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)8);
        for(int i = 0; i < bukkitInventory.getSize(); i++) {
            if(!this.slots.containsKey(i)) {
                bukkitInventory.setItem(i, filler);
            }
        }

        return bukkitInventory;
    }

    @Override
    public InventorySlot getSlot(int id) {
        return this.slots.get(id);
    }

    @Override
    public void setSlot(int id, InventorySlot slot) {
        this.slots.put(id, slot);
    }

    @Override
    public void setRows(int s) {
        this.mForceRows = s;
    }

    @Override
    public int getRows() {
        return 0;
    }
}
