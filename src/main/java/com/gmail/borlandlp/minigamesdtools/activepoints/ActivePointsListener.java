package com.gmail.borlandlp.minigamesdtools.activepoints;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class ActivePointsListener implements Listener {
    private Map<String, String> playersPosCache = new HashMap<>();//nickname -> coordX + "" + coordY + "" + coordZ
    private ActivePointController controller;

    public ActivePointsListener(ActivePointController controller) {
        this.controller = controller;
    }

    @EventHandler(
            priority = EventPriority.MONITOR,
            ignoreCancelled = true
    )
    public void onPlayerMove(PlayerMoveEvent event) {
        String curXYZ = event.getTo().getBlockX() + "" + event.getTo().getBlockY() + "" + event.getTo().getBlockZ();
        if(!playersPosCache.containsKey(event.getPlayer().getName()) || !playersPosCache.get(event.getPlayer().getName()).equals(curXYZ)) {
            playersPosCache.put(event.getPlayer().getName(), curXYZ);
            if(this.controller.getStaticPointsCache().contains(event.getTo())) {
                ActivePoint activePoint = this.controller.getStaticPointsCache().get(event.getTo());
                if(activePoint.isPerformEntityIntersection()) {
                    activePoint.performIntersect(event.getPlayer());
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(this.controller.getStaticPointsCache().contains(event.getBlock().getLocation())) {
            ActivePoint activePoint = this.controller.getStaticPointsCache().get(event.getBlock().getLocation());
            if(activePoint.isPerformDamage()) {
                activePoint.performDamage(event.getPlayer(), 1.0D);
            }

            if(!activePoint.isDamageable()) {
                event.setCancelled(true);
            }
        }
    }

    /* <freeing memory from disconnected players> */
    @EventHandler
    public void onPlayerDisc(PlayerQuitEvent event) {
        playersPosCache.remove(event.getPlayer().getName());
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        playersPosCache.remove(event.getPlayer().getName());
    }
    /* </freeing memory from disconnected players> */
}
