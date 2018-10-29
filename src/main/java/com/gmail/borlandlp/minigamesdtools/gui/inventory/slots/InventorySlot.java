package com.gmail.borlandlp.minigamesdtools.gui.inventory.slots;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public interface InventorySlot {
    String getID();
    void setID(String ID);
    Material getMaterial();
    void setMaterial(Material material);
    void setDamage(short d);
    short getDamage();
    void setMeta(ItemMeta im);
    ItemMeta getMeta();
    int getAmount();
    void setAmount(int a);
    ItemStack build();
    void performClick(Player player);
}
