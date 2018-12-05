package com.gmail.borlandlp.minigamesdtools.listener;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaPlayersRelative;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.*;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase;
import com.gmail.borlandlp.minigamesdtools.events.ArenaGameEndedEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

public class Events implements Listener {

    @EventHandler
    public void onArenaGameEnded(ArenaGameEndedEvent event) {
        MinigamesDTools.getInstance().getArenaAPI().restartArena(event.getArena().getName());
    }

    @EventHandler
    public void onInterract(PlayerInteractEvent event) {
        if(event.getItem() == null || event.getItem().getType() != Material.BOW) {
            return;
        }

        ArenaBase arena = MinigamesDTools.getInstance().getArenaAPI().getArenaOf(event.getPlayer());
        if(arena == null) {
            return;
        }

        if(arena.getState() == ArenaBase.STATE.COUNTDOWN || arena.getState() == ArenaBase.STATE.PAUSED) {
            ItemStack oldItem = event.getPlayer().getInventory().getItemInMainHand().clone();
            event.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            event.getPlayer().sendMessage("{match_hsnt_started_yet}");
            Bukkit.getScheduler().scheduleSyncDelayedTask(MinigamesDTools.getInstance(), () -> event.getPlayer().getInventory().setItemInMainHand(oldItem), 1L);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        ArenaBase arena = MinigamesDTools.getInstance().getArenaAPI().getArenaOf(event.getPlayer());
        if(arena == null) {
            return;
        }

        if((arena.getGameRules().beforeFightDisableMoving && arena.getState() == ArenaBase.STATE.COUNTDOWN) || arena.getState() == ArenaBase.STATE.PAUSED) {
            if(event.getFrom().getX() != event.getTo().getX() || event.getFrom().getY() != event.getTo().getY() || event.getFrom().getZ() != event.getTo().getZ() ) {
                event.setCancelled(true);
            }
        }
    }

    /*
    * <PlayerDMG>
    * */
    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event) {
        if(!(event.getEntity() instanceof Player)) {
            return;
        } else if(event instanceof EntityDamageByEntityEvent) {
            return;
        }

        Player player = (Player)event.getEntity();
        ArenaBase arena = MinigamesDTools.getInstance().getArenaAPI().getArenaOf(player);
        if(arena == null) {
            return;
        }

        if(arena.getState() != ArenaBase.STATE.EMPTY) {
            if((player.getHealth() - event.getFinalDamage()) <= 0D) {
                event.setDamage(0D);
                ArenaPlayerDeathLocalEvent arenaEvent = new ArenaPlayerDeathLocalEvent(player);
                arena.getEventAnnouncer().announce(arenaEvent);
                if(arenaEvent.isCancelled()) {
                    event.setCancelled(true);
                }
            } else {
                ArenaPlayerDamagedLocalEvent arenaEvent = new ArenaPlayerDamagedLocalEvent(player, event.getFinalDamage());
                arena.getEventAnnouncer().announce(arenaEvent);
                if(arenaEvent.isCancelled()) {
                    event.setCancelled(true);
                }
            }

        } else if(arena.getState() == ArenaBase.STATE.PAUSED && arena.getState() == ArenaBase.STATE.COUNTDOWN) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageByEntityEvent event) {
        if(!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player)event.getEntity();
        ArenaBase arena = MinigamesDTools.getInstance().getArenaAPI().getArenaOf(player);
        if(arena == null) {
            return;
        }

        if(arena.getState() != ArenaBase.STATE.EMPTY) {
            if(event.getDamager() instanceof Player) {
                if(arena.getTeamController().getPlayersRelative(player, (Player) event.getDamager()) == ArenaPlayersRelative.TEAMMATE) {
                    if(!arena.getTeamController().getTeamOf(player).friendlyFireAllowed()) {
                        event.setCancelled(true);
                        event.getDamager().sendMessage("{attack_teammate_msg}");
                        return;
                    }
                }
            }

            if((player.getHealth() - event.getFinalDamage()) <= 0.0D) {
                event.setDamage(0D);
                event.setCancelled(true);
                ArenaPlayerKilledLocalEvent arenaEvent = new ArenaPlayerKilledLocalEvent(player, event.getDamager());
                arena.getEventAnnouncer().announce(arenaEvent);
            } else {
                ArenaEntityDamagePlayerLocalEvent arenaEvent = new ArenaEntityDamagePlayerLocalEvent(event.getDamager(), player, event.getFinalDamage());
                arena.getEventAnnouncer().announce(arenaEvent);
                if(arenaEvent.isCancelled()) {
                    event.setCancelled(true);
                    event.setDamage(0D);
                }
            }
        } else if(arena.getState() == ArenaBase.STATE.PAUSED || arena.getState() == ArenaBase.STATE.COUNTDOWN) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        if(event.getEntity() != null) {
            Player player = event.getEntity();
            ArenaBase arena = MinigamesDTools.getInstance().getArenaAPI().getArenaOf(player);
            if(arena == null) {
                return;
            }

            ArenaPlayerDeathLocalEvent arenaEvent = new ArenaPlayerDeathLocalEvent(player);
            arena.getEventAnnouncer().announce(arenaEvent);
        }
    }

    /*
    * </playerDmg>
    * */

    @EventHandler
    public void onFoodLevelChangeEvent(FoodLevelChangeEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            ArenaBase arena = MinigamesDTools.getInstance().getArenaAPI().getArenaOf(player);
            if(arena != null && arena.getState() != ArenaBase.STATE.EMPTY && arena.getGameRules().hungerDisable) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBedjoin(PlayerBedEnterEvent event) {
        ArenaBase arena = MinigamesDTools.getInstance().getArenaAPI().getArenaOf(event.getPlayer());
        if(arena != null && arena.getState() != ArenaBase.STATE.PAUSED) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        ArenaBase arena = MinigamesDTools.getInstance().getArenaAPI().getArenaOf(event.getPlayer());
        if(arena != null && arena.getState() != ArenaBase.STATE.EMPTY) {
            ArenaPlayerLeaveLocalEvent arenaEvent = new ArenaPlayerLeaveLocalEvent(event.getPlayer());
            arena.getEventAnnouncer().announce(arenaEvent);
        }
    }


    @EventHandler
    public void onLogout(PlayerQuitEvent event) {
        ArenaBase arena = MinigamesDTools.getInstance().getArenaAPI().getArenaOf(event.getPlayer());
        if(arena != null && arena.getState() != ArenaBase.STATE.EMPTY) {
            ArenaPlayerLeaveLocalEvent arenaEvent = new ArenaPlayerLeaveLocalEvent(event.getPlayer());
            arena.getEventAnnouncer().announce(arenaEvent);
        }
    }

    /*
    @EventHandler
    public void onClickEvent(InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();
        if(player == null) {
            return;
        }
        if(!MinigamesDTools.getInstance().getArenaAPI().getCacheManager().contains(player)) {

            if(event.getCurrentItem() == null) {
                return;
            } else if(!event.getCurrentItem().hasItemMeta()) {
                return;
            } else if(!event.getCurrentItem().getItemMeta().hasDisplayName()) {
                return;
            }

            if(event.getCurrentItem().getItemMeta().getDisplayName().equals("[arena]")) {
                event.getWhoClicked().sendMessage(MinigamesDTools.getPrefix() + ChatColor.RED + " С арены запрещено выносить вещи. Ваш предмет был заменён на землю.");
                event.getCurrentItem().setType(Material.DIRT);
                for(Enchantment enchantment : event.getCurrentItem().getEnchantments().keySet()) {
                    event.getCurrentItem().removeEnchantment(enchantment);
                }
                event.getCurrentItem().setItemMeta(null);
            }
        }
    }
    */

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent event) {
        ArenaBase arena = MinigamesDTools.getInstance().getArenaAPI().getArenaOf(event.getPlayer());
        if(arena != null && !arena.getGameRules().playerCanItemDrop && arena.getState() != ArenaBase.STATE.EMPTY) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPickupDrop(EntityPickupItemEvent event) {
        if(!(event.getEntity() instanceof Player)) {
            return;
        }

        ArenaBase arena = MinigamesDTools.getInstance().getArenaAPI().getArenaOf((Player) event.getEntity());
        if(arena != null && !arena.getGameRules().playerCanItemPickup && arena.getState() != ArenaBase.STATE.EMPTY) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDamage(PlayerItemDamageEvent event) {
        ArenaBase arena = MinigamesDTools.getInstance().getArenaAPI().getArenaOf(event.getPlayer());
        if(arena == null) {
            return;
        }

        ArenaBase.STATE arenaState = arena.getState();
        if(arenaState == null) {
            Debug.print(Debug.LEVEL.WARNING, "Detected invalid null state for arena[name:" + arena.getName() + "]");
        } else if(arenaState == ArenaBase.STATE.PAUSED || arenaState == ArenaBase.STATE.COUNTDOWN) {
            event.getPlayer().sendMessage("{damage_reject_while_cooldown_msg}");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        ArenaBase arena = MinigamesDTools.getInstance().getArenaAPI().getArenaOf(event.getPlayer());
        if(arena != null && (arena.getState() == ArenaBase.STATE.COUNTDOWN || arena.getState() == ArenaBase.STATE.PAUSED)) {
            if(event.getFrom().distance(event.getTo()) > 1.0D) {
                event.setCancelled(true);
                event.getPlayer().sendMessage("{teleport_reject_msg}");
            }
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        /*if(event.getMessage().equalsIgnoreCase("arena")) return;
        else if(event.getMessage().equalsIgnoreCase("skill")) return;

        ExampleTeam team = (ExampleTeam)MinigamesDTools.getInstance().getArenaAPI().getCacheManager().get(event.getPlayer());
        if(team != null && (team.getArenaTemplate().getState() == ArenaBase.STATE.COUNTDOWN || team.getArenaTemplate().getState() == ArenaBase.STATE.IN_PROGRESS)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("[TW_ARENA] Запрещён ввод команд во время игры.");
        }*/
    }

    @EventHandler
    public void onCommand(EntityRegainHealthEvent event) {
        if(!(event.getEntity() instanceof Player)) return;

        ArenaBase arena = MinigamesDTools.getInstance().getArenaAPI().getArenaOf((Player) event.getEntity());
        if(arena != null && !arena.getGameRules().playerCanRegainHealth) {
            event.setCancelled(true);
        }
    }

    @EventHandler(
            priority = EventPriority.HIGHEST,
            ignoreCancelled = true
    )
    public void WeatherChangeEvent(WeatherChangeEvent event) {
        if (event.toWeatherState()) {
            event.setCancelled(true);
            event.getWorld().setWeatherDuration(0);
            event.getWorld().setThundering(false);
        }
    }
}
