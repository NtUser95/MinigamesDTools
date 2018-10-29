package com.gmail.borlandlp.minigamesdtools.gui.inventory.slots;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class AbstractSlot implements InventorySlot {
    private Material material;
    private int amount = 1;
    private String id;
    private short damage = 0;
    private ItemMeta meta;

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    @Override
    public Material getMaterial() {
        return this.material;
    }

    @Override
    public void setMaterial(Material m) {
        this.material = m;
    }

    @Override
    public int getAmount() {
        return this.amount;
    }

    @Override
    public void setAmount(int a) {
        this.amount = a;
    }

    @Override
    public ItemStack build() {
        ItemStack is = new ItemStack(this.material, this.amount, this.damage);
        is.setItemMeta(this.meta);
        return is;
    }

    @Override
    public void setDamage(short d) {
        this.damage = d;
    }

    @Override
    public short getDamage() {
        return this.damage;
    }

    @Override
    public void setMeta(ItemMeta im) {
        this.meta = im;
    }

    @Override
    public ItemMeta getMeta() {
        return this.meta;
    }
}
