package com.gmail.borlandlp.minigamesdtools.gui.hotbar;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.SlotItem;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.utils.Leveling;
import net.minecraft.server.v1_12_R1.PacketPlayOutExperience;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public abstract class Hotbar {
    protected SlotItem[] slots = new SlotItem[9];
    protected Deque<SlotItem> itemsInQueue = new ArrayDeque<>();
    protected Player player;

    public Hotbar(Player player) {
        this.player = player;
    }

    public Hotbar(Player player, SlotItem[] items) {
        this.player = player;
        this.slots = items;
    }

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
        this.updateSlotsQueue();
    }

    public void performAction(int slotID) {
        Debug.print(Debug.LEVEL.NOTICE,"[SkyBattle] performAction slotID:" + slotID + " for player:" + this.player.getDisplayName());
        if(slotID < this.slots.length && slotID >= 0 && this.slots[slotID] != null) {
            this.slots[slotID].performClick(this.player);
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

    public void draw() {
        ItemStack[] drawData = this.getDrawData();
        ItemStack[] inventory = this.player.getInventory().getContents();

        int heldSlot = player.getInventory().getHeldItemSlot();
        if(this.slots[heldSlot] != null && this.slots[heldSlot].inCooldown()) {
            float percent = ((float)(this.slots[heldSlot].getCooldownTime() - this.slots[heldSlot].getCooldownRemain()) / this.slots[heldSlot].getCooldownTime());
            int secRemain = (int) (this.slots[heldSlot].getCooldownRemain() / 1000);
            Leveling.ExperienceContainer value = Leveling.calculateWithPercentage((int) (this.slots[heldSlot].getCooldownRemain() / 1000), percent);
            PacketPlayOutExperience packet = new PacketPlayOutExperience(percent, (int) value.total_exp, secRemain);

            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }

        if(!isIdentDrawData(drawData, inventory)) {
            for(int i = 0; i < 9; i++) {
                inventory[i] = drawData[i];
            }
            player.getInventory().setContents(inventory);
        }
    }

    private boolean isIdentDrawData(ItemStack[] drawData, ItemStack[] inventory) {
        for(int i = 0; i < 9; i++) {
            if((inventory[i] == null && drawData[i] != null) || (inventory[i] != null && drawData[i] == null)) {
                return false;
            } else if(inventory[i] != null && drawData[i] != null) {
                boolean materialChanged = inventory[i].getType() != drawData[i].getType();
                boolean amountChanged = inventory[i].getAmount() != drawData[i].getAmount();
                if(materialChanged || amountChanged) {
                    return false;
                }
            }
        }

        return true;
    }

    private void updateSlotsQueue() {
        for (int i = 0; i < this.slots.length-1; i++) { // use 8 slots. 9 slot - reserved for correct work itemHeldEvent
            if(this.slots[i] == null && this.itemsInQueue.size() > 0) {
                this.slots[i] = this.itemsInQueue.poll();
            }
        }
    }
}
