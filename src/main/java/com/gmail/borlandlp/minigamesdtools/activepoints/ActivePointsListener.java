package com.gmail.borlandlp.minigamesdtools.activepoints;

import com.gmail.borlandlp.minigamesdtools.events.BlockDamageByEntityEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class ActivePointsListener implements Listener {
    private Map<String, String> playersPosCache = new HashMap<>();//nickname -> coordX + "" + coordY + "" + coordZ
    private ActivePointController controller;

    private Map<LivingEntity, Long> damageCooldowns = new Hashtable<>();
    private Map<LivingEntity, Long> intersectionCooldowns = new Hashtable<>();
    private long damageCd_Ms = 500; // 0.5 sec
    private long intersectionCd_Ms = 1000; // 1 sec

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
                //check cd
                if(!this.intersectionCooldowns.containsKey(event.getPlayer())) {
                    this.intersectionCooldowns.put(event.getPlayer(), System.nanoTime());
                } else {
                    long msDiff = (System.nanoTime() - this.intersectionCooldowns.get(event.getPlayer())) / 1000000;
                    if(msDiff <= this.intersectionCd_Ms) {
                        return;
                    } else {
                        this.intersectionCooldowns.put(event.getPlayer(), System.nanoTime());
                    }
                }

                ActivePoint activePoint = this.controller.getStaticPointsCache().get(event.getTo());
                if(activePoint.isPerformEntityIntersection()) {
                    activePoint.performIntersect(event.getPlayer());
                }
            }
        }
    }

    /*
    * Ломание блока рукой игрока
    * */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(this.controller.getStaticPointsCache().contains(event.getBlock().getLocation())) {
            //check cd
            if(!this.damageCooldowns.containsKey(event.getPlayer())) {
                this.damageCooldowns.put(event.getPlayer(), System.nanoTime());
            } else {
                long msDiff = (System.nanoTime() - this.damageCooldowns.get(event.getPlayer())) / 1000000;
                if(msDiff <= this.damageCd_Ms) {
                    event.setCancelled(true);
                    return;
                } else {
                    this.damageCooldowns.put(event.getPlayer(), System.nanoTime());
                }
            }

            ActivePoint activePoint = this.controller.getStaticPointsCache().get(event.getBlock().getLocation());
            if(activePoint.isPerformDamage()) {
                activePoint.performDamage(event.getPlayer(), 1.0D);
            }

            if(!activePoint.isDamageable()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockDamage(BlockDamageByEntityEvent event) {
        if(this.controller.getStaticPointsCache().contains(event.getBlock().getLocation())) {
            //check cd
            if(!this.damageCooldowns.containsKey(event.getEntity())) {
                this.damageCooldowns.put(event.getEntity(), System.nanoTime());
            } else {
                long msDiff = (System.nanoTime() - this.damageCooldowns.get(event.getEntity())) / 1000000;
                if(msDiff <= this.damageCd_Ms) {
                    event.setCancelled(true);
                    return;
                } else {
                    this.damageCooldowns.put(event.getEntity(), System.nanoTime());
                }
            }

            ActivePoint activePoint = this.controller.getStaticPointsCache().get(event.getBlock().getLocation());
            if(activePoint.isPerformDamage()) {
                activePoint.performDamage(event.getEntity(), 1.0D);
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
