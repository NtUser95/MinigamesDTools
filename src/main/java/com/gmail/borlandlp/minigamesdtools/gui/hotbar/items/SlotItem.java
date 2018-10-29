package com.gmail.borlandlp.minigamesdtools.gui.hotbar.items;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Instant;

public abstract class SlotItem {
    protected int cooldownTime;
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

    public int getCooldownTime() {
        return cooldownTime;
    }

    public void setCooldownTime(int cooldownTime) {
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
        long cdRemain = Instant.now().getEpochSecond() - this.getLastClickTime();
        if(cdRemain > this.getCooldownTime()) {
            this.setLastClickTime(Instant.now().getEpochSecond());
            boolean result = this.use(player);
            if(result && !this.isInfiniteSlot()) {
                this.setAmount(this.getAmount() - 1);
            }
        } else {
            player.sendMessage("DBG:CD->" + (this.getCooldownTime() - cdRemain));//DEBUG
        }
    }

    public ItemStack getDrawData() {
        ItemStack icon;
        if((this.getLastClickTime() + this.getCooldownTime()) <= Instant.now().getEpochSecond()) {
            icon = this.getActiveIcon().clone();
            icon.setAmount(this.getAmount());
        } else {//item in cooldown
            icon = this.getUnactiveIcon().clone();
            icon.setAmount((int)((this.getLastClickTime() + this.getCooldownTime()) - Instant.now().getEpochSecond()));
        }

        return icon;
    }

    public abstract boolean use(Player player);
}
