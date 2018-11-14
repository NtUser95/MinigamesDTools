package com.gmail.borlandlp.minigamesdtools.gui.hotbar;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.SlotItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayDeque;
import java.util.Deque;

public abstract class Hotbar {
    protected SlotItem[] slots = new SlotItem[9];
    protected Deque<SlotItem> itemsInQueue = new ArrayDeque<>();

    public void addSlot(SlotItem slotItem) {
        this.itemsInQueue.add(slotItem);
    }

    public void setSlot(int index, SlotItem slot) throws Exception {
        if(index < this.slots.length && index >= 0) {
            this.slots[index] = slot;
        } else {
            throw new Exception("HotbarSlot with ID: '" + slot.getName() + "' has an incorrect index[" + index + "] or is out of bounds [correct -> 0-8]");
        }
    }

    public void update() {
        for (int i = 0; i < this.slots.length-1; i++) { // use 8 slots. 9 slot - reserved for correct work itemHeldEvent
            if(this.slots[i] == null && this.itemsInQueue.size() > 0) {
                this.slots[i] = this.itemsInQueue.poll();
            }
        }
    }

    public void performAction(Player player, int slotID) {
        Debug.print(Debug.LEVEL.NOTICE,"[SkyBattle] performAction slotID:" + slotID + " for player:" + player.getDisplayName());
        if(slotID < this.slots.length && slotID >= 0 && this.slots[slotID] != null) {
            this.slots[slotID].performClick(player);
            if(this.slots[slotID].getAmount() < 1) {
                this.slots[slotID] = null;
            }
        }
    }

    public ItemStack[] getDrawData() {
        ItemStack[] data = new ItemStack[9];
        for(int i = 0; i < this.slots.length; i++) {
            if(this.slots[i] != null) {
                data[i] = this.slots[i].getDrawData();
            }
        }

        return data;
    }
}
