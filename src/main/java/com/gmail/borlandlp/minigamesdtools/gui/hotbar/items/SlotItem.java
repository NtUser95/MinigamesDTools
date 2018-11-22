package com.gmail.borlandlp.minigamesdtools.gui.hotbar.items;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Instant;

public abstract class SlotItem {
    protected Long cooldownTime;
    protected ItemStack activeIcon;
    protected ItemStack unactiveIcon;
    protected int amount;
    protected String ID;
    protected long lastClickTime;
    protected String name;
    protected boolean infiniteSlot = false;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setActiveIcon(ItemStack activeIcon) {
        this.activeIcon = activeIcon;
    }

    public void setUnactiveIcon(ItemStack unactiveIcon) {
        this.unactiveIcon = unactiveIcon;
    }

    public long getCooldownTime() {
        return cooldownTime;
    }

    public void setCooldownTime(long cooldownTime) {
        this.cooldownTime = cooldownTime;
    }

    public boolean isInfiniteSlot() {
        return infiniteSlot;
    }

    public void setInfiniteSlot(boolean infiniteSlot) {
        this.infiniteSlot = infiniteSlot;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ItemStack getUnactiveIcon() {
        return this.unactiveIcon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemStack getActiveIcon() {
        return this.activeIcon;
    }

    public long getLastClickTime() {
        return lastClickTime;
    }

    public void setLastClickTime(long lastClickTime) {
        this.lastClickTime = lastClickTime;
    }

    public void performClick(Player player) {
        long msDiff = (System.nanoTime() - this.getLastClickTime()) / 1000000;
        if(msDiff > this.getCooldownTime()) {
            this.setLastClickTime(System.nanoTime());
            boolean result = this.use(player);
            if(result && !this.isInfiniteSlot()) {
                this.setAmount(this.getAmount() - 1);
            }
        } else {
            player.sendMessage("DBG:CD->" + (msDiff));//DEBUG
        }
    }

    public ItemStack getDrawData() {
        ItemStack icon;
        long msDiff = (System.nanoTime() - this.getLastClickTime()) / 1000000;
        if(msDiff > this.getCooldownTime()) {
            icon = this.getActiveIcon().clone();
            icon.setAmount(this.getAmount());
        } else {//item in cooldown
            icon = this.getUnactiveIcon().clone();
            icon.setAmount(Long.valueOf((this.getCooldownTime() - msDiff) / 1000).intValue());
        }

        return icon;
    }

    public abstract boolean use(Player player);

    public String toString() {
        StringBuilder str = new StringBuilder()
                .append("{")
                .append("activeIcon=").append(this.getActiveIcon().getType().name())
                .append(", unactiveIcon=").append(this.getUnactiveIcon().getType().name())
                .append(", amount=").append(this.getAmount())
                .append(", infiniteSlot=").append(this.isInfiniteSlot())
                .append(", name=").append(this.getName())
                .append("}");

        return str.toString();
    }
}
