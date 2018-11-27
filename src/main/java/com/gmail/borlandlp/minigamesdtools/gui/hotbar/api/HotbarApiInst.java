package com.gmail.borlandlp.minigamesdtools.gui.hotbar.api;

import com.gmail.borlandlp.minigamesdtools.APIComponent;
import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.Hotbar;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.HotbarListener;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.type.HeldHotbar;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.utils.Leveling;
import net.minecraft.server.v1_12_R1.PacketPlayOutExperience;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
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
        }.runTaskTimer(MinigamesDTools.getInstance(), 0, 5);
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
        for(Player player : new ArrayList<>(this.getAll().keySet())) {
            if(player != null && !player.isDead()) {
                Hotbar hotbar = this.getAll().get(player);
                try {
                    hotbar.update();
                    hotbar.draw();
                } catch (Exception e) {
                    e.printStackTrace();
                    this.unbindHotbar(player);
                    Debug.print(Debug.LEVEL.WARNING, "Player " + player.getName() + " was unbinded due to problems with his hotbar.");
                }
            }
        }
    }

    private void clearPlayerHotbar(Player p) {
        for (int i = 0; i < 9; i++) {
            p.getInventory().setItem(i, null);
        }
    }

    @Override
    public void bindHotbar(Hotbar hotbar, Player player) {
        Debug.print(Debug.LEVEL.NOTICE, "Bind hotbar for Player[name:" + player.getName() + "]");
        if(hotbar instanceof HeldHotbar) {
            player.getInventory().setHeldItemSlot(8);
        }

        this.players.put(player, hotbar);

        PacketPlayOutExperience packet = new PacketPlayOutExperience((float) 0, 0, 0);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    @Override
    public void unbindHotbar(Player player) {
        if(!this.isBindedPlayer(player)) {
            return;
        }

        Debug.print(Debug.LEVEL.NOTICE, "Unbind hotbar for Player[name:" + player.getName() + "]");
        this.clearPlayerHotbar(player);
        this.players.remove(player);

        PacketPlayOutExperience packet = new PacketPlayOutExperience(player.getExp(), player.getTotalExperience(), player.getLevel());
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
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
