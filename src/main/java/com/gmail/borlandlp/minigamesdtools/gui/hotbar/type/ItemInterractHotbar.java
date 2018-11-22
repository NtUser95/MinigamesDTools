package com.gmail.borlandlp.minigamesdtools.gui.hotbar.type;

import com.gmail.borlandlp.minigamesdtools.gui.hotbar.Hotbar;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.SlotItem;
import org.bukkit.entity.Player;

public class ItemInterractHotbar extends Hotbar {
    public ItemInterractHotbar(Player player) {
        super(player);
    }

    @Override
    public void update() {
        super.update();

        this.updateXpReloadStatus();
    }

    private void updateXpReloadStatus() {
        /*long cdRemain = Instant.now().getEpochSecond() - this.getLastClickTime();
        if(cdRemain > this.getCooldownTime()) {

        SlotItem slotItem = this.slots[this.player.getInventory().getHeldItemSlot()];
        boolean nullFlag = slotItem == null && this.player.getExp() > 0.0F;
        boolean cdCompleted = slotItem != null && this.player.getExp() > 0.0F && slotItem.coo
        if(nullFlag || ) {
            this.player.setExp(0F);
        }*/
    }
}
