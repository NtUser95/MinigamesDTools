package com.gmail.borlandlp.minigamesdtools.gui.inventory;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.DataProvider;
import com.gmail.borlandlp.minigamesdtools.events.PlayerRequestOpenInvGUIEvent;
import com.gmail.borlandlp.minigamesdtools.gui.inventory.slots.InventorySlot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.Instant;
import java.util.HashMap;

public class InventoryListener implements Listener {
    private HashMap<String, Long> playersClickLog = new HashMap<>();

    @EventHandler
    public void onInvRequest(PlayerRequestOpenInvGUIEvent event) {
        if(!MinigamesDTools.getInstance().getInventoryGUICreatorHub().containsRouteId2Creator(event.getPageID())) {
            Debug.print(Debug.LEVEL.WARNING, "Player[name:" + event.getPlayer().getName() + "] requested unknown Page[ID:" + event.getPageID() + "]");
            return;
        }

        DrawableInventory drawableInventory = null;
        try {
            AbstractDataProvider dataProvider = new DataProvider();
            dataProvider.set("player", event.getPlayer());
            drawableInventory = MinigamesDTools.getInstance().getInventoryGUICreatorHub().createInventory(event.getPageID(), dataProvider);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(drawableInventory != null) {
            MinigamesDTools.getInstance().getInventoryGUI_API().show(drawableInventory, event.getPlayer());
        } else {
            Debug.print(Debug.LEVEL.NOTICE, "Internal error. DrawableInventory cant be null.");
        }
    }

    @EventHandler(
            priority = EventPriority.LOW,
            ignoreCancelled = true
    )
    public void onCloseInv(InventoryCloseEvent event) {
        if(MinigamesDTools.getInstance().getInventoryGUI_API().isView(((Player)event.getPlayer()))) {
            MinigamesDTools.getInstance().getInventoryGUI_API().unregister((Player)event.getPlayer());
        }
    }

    @EventHandler(
            priority = EventPriority.LOW
    )
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        if(MinigamesDTools.getInstance().getInventoryGUI_API().isView((event.getPlayer()))) {
            MinigamesDTools.getInstance().getInventoryGUI_API().close(event.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerKickEvent(PlayerQuitEvent event) {
        if(MinigamesDTools.getInstance().getInventoryGUI_API().isView((event.getPlayer()))) {
            MinigamesDTools.getInstance().getInventoryGUI_API().close(event.getPlayer());
        }
    }

    @EventHandler(
            priority = EventPriority.LOW,
            ignoreCancelled = true
    )
    public void onInvClickItem(InventoryClickEvent event) {
        if(event.getClickedInventory() == null) {
            return;
        } else if(MinigamesDTools.getInstance().getInventoryGUI_API().isView((Player) event.getWhoClicked())) {

            if(event.getWhoClicked().getOpenInventory().getTopInventory() != event.getClickedInventory()) {
                return;
            }

            event.setCancelled(true);

            if(event.getCurrentItem().getType().name().equalsIgnoreCase("air")) {
                return;
            }

            DrawableInventory drawableInventory = MinigamesDTools.getInstance().getInventoryGUI_API().getInventory((Player)event.getWhoClicked());

            String playerName = event.getWhoClicked().getName();
            if(!this.playersClickLog.containsKey(playerName) || (this.playersClickLog.get(playerName) + 1) <= Instant.now().getEpochSecond()) {
                this.playersClickLog.put(playerName, Instant.now().getEpochSecond());
            } else {
                event.getWhoClicked().sendMessage("Пожалуйста, не кликайте так часто! :( Минимальное время между кликами - 1 секунда.");
                this.playersClickLog.put(playerName, Instant.now().getEpochSecond());
                return;
            }

            InventorySlot slot = drawableInventory.getSlot(event.getSlot());
            if(slot != null) {
                slot.performClick((Player) event.getWhoClicked());
            }

        } else if((event.getWhoClicked()).getOpenInventory() != null && MinigamesDTools.getInstance().getInventoryGUI_API().isView((Player) event.getWhoClicked()) && event.isShiftClick()) {
            event.setCancelled(true);
        }
    }
}
