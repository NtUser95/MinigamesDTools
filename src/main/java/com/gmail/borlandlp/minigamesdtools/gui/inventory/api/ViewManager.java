package com.gmail.borlandlp.minigamesdtools.gui.inventory.api;

import com.gmail.borlandlp.minigamesdtools.APIComponent;
import com.gmail.borlandlp.minigamesdtools.gui.inventory.DrawableInventory;
import com.gmail.borlandlp.minigamesdtools.gui.inventory.api.InventoryAPI;
import org.bukkit.entity.Player;

import java.util.Hashtable;
import java.util.Map;

public class ViewManager implements InventoryAPI, APIComponent {
    private Map<String, DrawableInventory> viewMap = new Hashtable<>();

    public void show(DrawableInventory drawableInventory, Player player) {
        if(player.getOpenInventory() != null) {
            player.getOpenInventory().close();
        }

        this.viewMap.put(player.getName(), drawableInventory);
        player.openInventory(drawableInventory.toBukkitInventory());
    }

    public DrawableInventory getInventory(Player player) {
        return this.viewMap.get(player.getName());
    }

    public boolean isView(Player player) {
        return this.viewMap.containsKey(player.getName());
    }

    public void close(Player player) {
        this.unregister(player);
    }

    public void unregister(Player player) {
        this.viewMap.remove(player.getName());
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onUnload() {

    }
}
