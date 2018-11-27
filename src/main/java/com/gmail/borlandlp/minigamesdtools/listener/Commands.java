package com.gmail.borlandlp.minigamesdtools.listener;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.activepoints.ActivePoint;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase;
import com.gmail.borlandlp.minigamesdtools.arena.exceptions.ArenaAlreadyStartedException;
import com.gmail.borlandlp.minigamesdtools.arena.team.TeamProvider;
import com.gmail.borlandlp.minigamesdtools.config.exception.InvalidPathException;
import com.gmail.borlandlp.minigamesdtools.creator.DataProvider;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.Hotbar;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.utils.Leveling;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.inventivetalent.glow.GlowAPI;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;

public class Commands implements CommandExecutor {
    public static BossBar bossBar = Bukkit.createBossBar("test", BarColor.RED, BarStyle.SOLID);
    public static Scoreboard scoreboard;

   public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
      if(cmd.getName().equalsIgnoreCase("arena")) {
          Player player = (Player) sender;

         if(sender instanceof ConsoleCommandSender) {
             sender.sendMessage("Вы не игрок!");
             return true;
         } else {

            Player p = (Player) sender;

            if(args[0].equalsIgnoreCase("lobby")) {
              MinigamesDTools.getInstance().getLobbyHubAPI().getLobbyByID("example_lobby").registerPlayer(player);
            } else if(args[0].equalsIgnoreCase("lobby_leave")) {
              MinigamesDTools.getInstance().getLobbyHubAPI().getLobbyByID("example_lobby").unregisterPlayer(player);
            } else if(args[0].equalsIgnoreCase("lobby_transfer")) {
              try {
                  MinigamesDTools.getInstance().getLobbyHubAPI().getLobbyByID("example_lobby").transferPlayer(player, MinigamesDTools.getInstance().getLobbyHubAPI().getLobbyByID("example_lobby2"));
              } catch (Exception e) {
                  e.printStackTrace();
              }
            } else if (!player.hasPermission("arena.admin")) {
                player.sendMessage(MinigamesDTools.getPrefix() + " Недостаточно прав для доступа.");
                return true;
            } else if (args[0].equalsIgnoreCase("fullreload")) {
                  try {
                      MinigamesDTools.getInstance().reload();
                  } catch (InvalidPathException e) {
                      e.printStackTrace();
                  }
                  player.sendMessage(MinigamesDTools.getPrefix() + " Произведена полная перезагрузка плагина.");
               return true;
            } else if(args[0].equalsIgnoreCase("enable")) {
                if(args.length == 2) {
                    ArenaBase arena = MinigamesDTools.getInstance().getArenaAPI().getArena(args[1]);
                    if(arena != null) {
                        arena.setEnabled(true);
                        player.sendMessage(MinigamesDTools.getPrefix() + " Арена с названием" + args[1] + " включена.");
                    } else {
                        player.sendMessage(MinigamesDTools.getPrefix() + " Арена с названием" + args[1] + " не найдена.");
                    }
                } else {
                    player.sendMessage(MinigamesDTools.getPrefix() + "/arena enable arena_name");
                }
            } else if(args[0].equalsIgnoreCase("disable")) {
                if(args.length == 2) {
                    ArenaBase arena = MinigamesDTools.getInstance().getArenaAPI().getArena(args[1]);
                    if(arena != null) {
                        arena.forceDisable();
                        player.sendMessage(MinigamesDTools.getPrefix() + " Арена с названием" + args[1] + " выключена.");
                    } else {
                        player.sendMessage(MinigamesDTools.getPrefix() + " Арена с названием" + args[1] + " не найдена.");
                    }
                } else {
                    player.sendMessage(MinigamesDTools.getPrefix() + "/arena enable arena_name");
                }
            } else if(args[0].equalsIgnoreCase("dbg_join") && p.hasPermission("arena.reload")) {

                MinigamesDTools.getInstance().getArenaAPI().arenaJoinRequest(args[1], p);
                p.sendMessage("dbg_join: " + args[1]);
                return true;

            } else if(args[0].equalsIgnoreCase("dbg_state")) {

                ArenaBase.STATE state = ArenaBase.STATE.valueOf(args[2]);
                MinigamesDTools.getInstance().getArenaAPI().getArena(args[1]).setState(state);
                p.sendMessage("dbg_state:" + args[1] + " " + MinigamesDTools.getInstance().getArenaAPI().getArena(args[1]));
                return true;

            } else if(args[0].equalsIgnoreCase("dbg_members")) {
                 p.sendMessage("===dbg_teams===");
                 for(TeamProvider team : MinigamesDTools.getInstance().getArenaAPI().getArena(args[1]).getTeamController().getTeams()) {
                     p.sendMessage("dbg_teams->" + args[1] + ":" + team.getName() + "#" + team.getPlayers());
                 }

                 return true;
            } else if(args[0].equalsIgnoreCase("dbg_start")) {
                 p.sendMessage("===arena_start===");
                try {
                    MinigamesDTools.getInstance().getArenaAPI().getArena(args[1]).startArena();
                } catch (ArenaAlreadyStartedException e) {
                    e.printStackTrace();
                }

                return true;
             } else if(args[0].equalsIgnoreCase("hotbar")) {
                  try {
                      Hotbar hotbar = MinigamesDTools.getInstance().getHotbarCreatorHub().createHotbar("example_skyhotbar", new DataProvider());
                      MinigamesDTools.getInstance().getHotbarAPI().bindHotbar(hotbar, p);
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
              } else if(args[0].equalsIgnoreCase("dbg_stop")) {
                 p.sendMessage("===arena_stop===");
                 p.sendMessage("stopped" + args[1]);
                 MinigamesDTools.getInstance().getArenaAPI().getArena(args[1]).forceDisable();

                 return true;
             } else if(args[0].equalsIgnoreCase("leave")) {
                 MinigamesDTools.getInstance().getArenaAPI().arenaLeaveRequest(p);

                 return true;
             } else if(args[0].equalsIgnoreCase("exp")) {
                int level = Integer.parseInt(args[1]);
                int perc = Integer.parseInt(args[2]);
                Leveling.ExperienceContainer value = Leveling.calculateWithPercentage(level, perc);
                PacketPlayOutExperience packet = new PacketPlayOutExperience((float) (perc / 100F), (int) value.total_exp, level);
                System.out.println(value.percent_exp + "#" + value.total_exp + "#" + level);
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
             }

            p.sendMessage("Wrong command");
            return true;
         }
      }

      return false;
   }

}
