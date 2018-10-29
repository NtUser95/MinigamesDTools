package com.gmail.borlandlp.minigamesdtools.gui.hotbar;

import com.gmail.borlandlp.minigamesdtools.gui.hotbar.api.HotbarAPI;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.type.HeldHotbar;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.type.ItemInterractHotbar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class HotbarListener implements Listener {
    private HotbarAPI hotbarAPI;

    public HotbarListener(HotbarAPI h) {
        this.hotbarAPI = h;
    }

    @EventHandler(ignoreCancelled = true)
    public void onItemHeld(PlayerItemHeldEvent event) {
        if(event.getNewSlot() != 8 && this.hotbarAPI.isBindedPlayer(event.getPlayer())) {
            Hotbar hotbar = this.hotbarAPI.getHotbar(event.getPlayer());
            if(hotbar instanceof HeldHotbar) {
                event.setCancelled(true);
                this.hotbarAPI.getHotbar(event.getPlayer()).performAction(event.getPlayer(), event.getNewSlot());
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onItemClicked(InventoryClickEvent event) {
        //System.out.println(event);
    }

    @EventHandler(ignoreCancelled = true)
    public void onItemDrop(PlayerDropItemEvent event) {
        if(this.hotbarAPI.isBindedPlayer(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    /*@EventHandler(ignoreCancelled = true)
    public void onMoveItem(InventoryMoveItemEvent event) {
        if(this.hotbarAPI.isBindedPlayer(event.getPlayer())) {
            event.setCancelled(true);
        }
    }*/

    @EventHandler
    public void onItemInterract(PlayerInteractEvent event) {
        if(event.getItem() != null && this.hotbarAPI.isBindedPlayer(event.getPlayer())) {
            Hotbar hotbar = this.hotbarAPI.getHotbar(event.getPlayer());
            if(hotbar instanceof ItemInterractHotbar) {
                event.setCancelled(true);
                this.hotbarAPI.getHotbar(event.getPlayer()).performAction(event.getPlayer(), event.getPlayer().getInventory().getHeldItemSlot());
            }
        }
    }
}
