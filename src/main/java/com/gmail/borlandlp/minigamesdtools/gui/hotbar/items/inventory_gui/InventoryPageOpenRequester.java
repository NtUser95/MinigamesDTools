package com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.inventory_gui;

import com.gmail.borlandlp.minigamesdtools.events.PlayerRequestOpenInvGUIEvent;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.SlotItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class InventoryPageOpenRequester extends SlotItem {
    private String pageId;

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    @Override
    public boolean use(Player player) {
        PlayerRequestOpenInvGUIEvent event = new PlayerRequestOpenInvGUIEvent(player, this.getPageId());
        Bukkit.getPluginManager().callEvent(event);
        return true;
    }
}
