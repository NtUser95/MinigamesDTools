package com.gmail.borlandlp.minigamesdtools.gui.hotbar.api;

import com.gmail.borlandlp.minigamesdtools.APIComponent;
import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.Hotbar;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.HotbarListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class HotbarApiInst implements HotbarAPI, APIComponent {
    private Map<Player, Hotbar> players = new Hashtable<>();
    private HotbarListener hotbarListener;
    private BukkitTask task;

    @Override
    public void onLoad() {
        this.hotbarListener = new HotbarListener(this);
        Bukkit.getServer().getPluginManager().registerEvents(this.hotbarListener, MinigamesDTools.getInstance());

        final HotbarApiInst task = this;
        this.task = new BukkitRunnable() {
            public void run() {
                task.update();
            }
        }.runTaskTimer(MinigamesDTools.getInstance(), 0, 20);
    }

    @Override
    public void onUnload() {
        HandlerList.unregisterAll(this.hotbarListener);
        this.task.cancel();

        for (Player player : this.getAll().keySet()) {
            try {
                this.unbindHotbar(player);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void update() {
        for(Player player : this.getAll().keySet()) {
            if(player != null) {
                Hotbar hotbar = this.getAll().get(player);
                hotbar.update();

                ItemStack[] drawData = hotbar.getDrawData();
                ItemStack[] inventory = player.getInventory().getContents();

                if(!isIdentDrawData(drawData, inventory)) {
                    for(int i = 0; i < 9; i++) {
                        inventory[i] = drawData[i];
                    }
                    player.getInventory().setContents(inventory);
                }

                // TODO: проверить кооректность работы и удалить старый кусок кода
                /*
                boolean inventoryChanged = false;

                for(int i = 0; i < 9; i++) {
                    if((inventory[i] == null && drawData[i] != null) || (inventory[i] != null && drawData[i] == null)) {
                        inventory[i] = drawData[i];
                        inventoryChanged = true;
                    } else if(inventory[i] != null && drawData[i] != null) {
                        boolean materialChanged = inventory[i].getType() != drawData[i].getType();
                        boolean amountChanged = inventory[i].getAmount() != drawData[i].getAmount();
                        if(materialChanged || amountChanged) {
                            inventory[i] = drawData[i];
                            inventoryChanged = true;
                        }
                    }
                }

                if(inventoryChanged) {
                    player.getInventory().setContents(inventory);
                }
                */
            }
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

    private void clearPlayerHotbar(Player p) {
        for (int i = 0; i < 9; i++) {
            p.getInventory().setItem(i, null);
        }
    }

    @Override
    public void bindHotbar(Hotbar hotbar, Player player) {
        Debug.print(Debug.LEVEL.NOTICE, "Bind hotbar for Player[name:" + player.getName() + "]");
        this.players.put(player, hotbar);
    }

    @Override
    public void unbindHotbar(Player player) {
        if(!this.isBindedPlayer(player)) {
            return;
        }

        Debug.print(Debug.LEVEL.NOTICE, "Unbind hotbar for Player[name:" + player.getName() + "]");
        this.clearPlayerHotbar(player);
        this.players.remove(player);
    }

    @Override
    public boolean isBindedPlayer(Player player) {
        return this.players.containsKey(player);
    }

    @Override
    public Hotbar getHotbar(Player player) {
        return this.players.get(player);
    }

    @Override
    public Map<Player, Hotbar> getAll() {
        return new HashMap<>(this.players);
    }
}
