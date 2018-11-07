package com.gmail.borlandlp.minigamesdtools.listener;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.activepoints.ActivePoint;
import com.gmail.borlandlp.minigamesdtools.arena.exceptions.ArenaAlreadyStartedException;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerJoinLocalEvent;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase;
import com.gmail.borlandlp.minigamesdtools.arena.team.TeamListener;
import com.gmail.borlandlp.minigamesdtools.arena.team.TeamProvider;
import com.gmail.borlandlp.minigamesdtools.config.exception.InvalidPathException;
import com.gmail.borlandlp.minigamesdtools.util.ArenaMathHelper;
import com.gmail.borlandlp.minigamesdtools.util.ArenaUtils;
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
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Commands implements CommandExecutor {
    public static BossBar bossBar = Bukkit.createBossBar("test", BarColor.RED, BarStyle.SOLID);

   public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
      if(cmd.getName().equalsIgnoreCase("arena")) {
          Player player = (Player) sender;

          if(sender instanceof ConsoleCommandSender) {
            sender.sendMessage("Вы не игрок!");
            return true;

         } else {

              Player p = (Player) sender;

              if(args[0].equalsIgnoreCase("test")) {
                  Player diost = sender.getServer().getPlayer("Diost");
                  Player quss = sender.getServer().getPlayer("Quss");

                  /*
                  double var1 = this.e - this.a.locX;
                  double var3 = this.f - (this.a.locY + (double)this.a.getHeadHeight());
                  double var5 = this.g - this.a.locZ;
                  double var7 = (double)MathHelper.sqrt(var1 * var1 + var5 * var5);
                  float var9 = (float)(MathHelper.c(var5, var1) * 57.2957763671875D) - 90.0F;
                  float var10 = (float)(-(MathHelper.c(var3, var7) * 57.2957763671875D));
                  this.a.pitch = this.a(this.a.pitch, var10, this.c);
                  this.a.aP = this.a(this.a.aP, var9, this.b);
                  */

                    // var1 = куда - откуда
                  double var1 = quss.getLocation().getX() - diost.getLocation().getX();
                  double var5 = quss.getLocation().getZ() - diost.getLocation().getZ();
                  float var9 = (float)(MathHelper.c(var5, var1) * 57.2957763671875D) - 90.0F;

                  System.out.println("yaw:" + p.getLocation().getYaw());
                  System.out.println("source:" + ((var9 < 0) ? var9 + 360 : var9));
              } else if(args[0].equalsIgnoreCase("bossbar")) {
                  bossBar.addPlayer(p);
              } else if(args[0].equalsIgnoreCase("title")) {

                  // tested
                  Player diost = sender.getServer().getPlayer("Diost");
                  Player quss = sender.getServer().getPlayer("Quss");

                  Location testLoc = new Location(Bukkit.getWorld("world"), -109D, 63D, 212D);
                  double currentDegreesDiff = ArenaMathHelper.degreesBetweenPlayerAndLocation(diost, testLoc);
                  double searchRange = 40D;
                  double pseudoRadarSize = 31; // 15 symbols <- 1 middle symbol -> 15 symbols
                  double maxDegreesDiff = 60;
                  double degreesDiffPercent = (currentDegreesDiff / (maxDegreesDiff * 2)) * 100D;
                  int drawSymbol = (int)((degreesDiffPercent / 100D) * pseudoRadarSize);

                  String[] strs = new String[(int)pseudoRadarSize];

                  Vector hopVec = player.getLocation().clone().getDirection().multiply(searchRange);
                  Location hopLoc = player.getLocation().clone().add(hopVec);
                  ArenaMathHelper.Interposition interposition = ArenaMathHelper.getInterpositionOfPoint(diost.getLocation(), hopLoc, testLoc);
                  if(interposition == ArenaMathHelper.Interposition.LEFTSIDE) {
                      strs[ (int)((pseudoRadarSize/2)-drawSymbol) ] = ChatColor.RED + "#";
                  } else if(interposition == ArenaMathHelper.Interposition.RIGHTSIDE) {
                      strs[ (int)((pseudoRadarSize/2)+drawSymbol) ] = ChatColor.RED + "#";
                  } else if(interposition == ArenaMathHelper.Interposition.UPSIDE) {
                      strs[ 16 ] = ChatColor.RED + "#";
                  }

                  System.out.print("angle_test:" + currentDegreesDiff + "#interposition:" + interposition + "#drawSymbol:" + drawSymbol);
                  System.out.println(diost.getLocation());
                  System.out.println(hopLoc);

                  int size = Integer.parseInt(args[1]);
                  StringBuilder stringBuilder = new StringBuilder();
                  for (int i = 0; i < pseudoRadarSize; i++) {
                      if(strs[i] == null) {
                          //stringBuilder.append(' ');
                          stringBuilder.append('A');
                      } else {System.out.println("is:" + i);
                          stringBuilder.append(strs[i]);
                      }
                  }
                  bossBar.setTitle(stringBuilder.toString());
              } else if(args[0].equalsIgnoreCase("lobby")) {
                  MinigamesDTools.getInstance().getLobbyHubAPI().getLobbyByID("example_lobby").registerPlayer(player);
              } else if(args[0].equalsIgnoreCase("lobby_leave")) {
                  MinigamesDTools.getInstance().getLobbyHubAPI().getLobbyByID("example_lobby").unregisterPlayer(player);
              } else if(args[0].equalsIgnoreCase("lobby_transfer")) {
                  try {
                      MinigamesDTools.getInstance().getLobbyHubAPI().getLobbyByID("example_lobby").transferPlayer(player, MinigamesDTools.getInstance().getLobbyHubAPI().getLobbyByID("example_lobby2"));
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
              } else if(args[0].equalsIgnoreCase("activepoints")) {
                  for(ActivePoint point : MinigamesDTools.getInstance().getActivePointsAPI().getAllPoints()) {
                      System.out.print(point.getName());
                  }
              }

              if (!player.hasPermission("arena.admin")) {
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
            } else if(args[0].equalsIgnoreCase("edit")) {

                if(args.length > 1) {

                    String arenaName = args[1];
                    String action = args[2];


                } else {
                    p.sendMessage("/arena edit <arena_name> <action>");
                    return true;
                }

            } else if(args[0].equalsIgnoreCase("dbg_msg")) {
                IChatBaseComponent comp = IChatBaseComponent.ChatSerializer.a("{\"text\":\"simplyteampvp message!\"}");
                PacketPlayOutChat packet = new PacketPlayOutChat(comp, ChatMessageType.GAME_INFO);
                ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
                return true;
            } else if(args[0].equalsIgnoreCase("dbg_join") && p.hasPermission("arena.reload")) {

                MinigamesDTools.getInstance().getArenaAPI().arenaJoinRequest(args[1], p);
                p.sendMessage("dbg_join: " + args[1]);
                return true;

            } else if(args[0].equalsIgnoreCase("dbg_force_join") && p.hasPermission("arena.reload")) {
                TeamProvider team = MinigamesDTools.getInstance().getArenaAPI().getArena("test1x1").getTeamController().getTeams().get(0);
                MinigamesDTools.getInstance().getArenaAPI().getArena("test1x1").getEventAnnouncer().announce(new ArenaPlayerJoinLocalEvent(player, team));
                p.sendMessage("dbg_force_join: test1x1 " + team.getName());
                return true;
            } else if(args[0].equalsIgnoreCase("dbg_state") && p.hasPermission("arena.reload")) {

                ArenaBase.STATE state = ArenaBase.STATE.valueOf(args[2]);
                MinigamesDTools.getInstance().getArenaAPI().getArena(args[1]).setState(state);
                p.sendMessage("dbg_state:" + args[1] + " " + MinigamesDTools.getInstance().getArenaAPI().getArena(args[1]));
                return true;

            } else if(args[0].equalsIgnoreCase("dbg_members") && p.hasPermission("arena.reload")) {
                 p.sendMessage("===dbg_teams===");
                 for(TeamProvider team : MinigamesDTools.getInstance().getArenaAPI().getArena(args[1]).getTeamController().getTeams()) {
                     p.sendMessage("dbg_teams->" + args[1] + ":" + team.getName() + "#" + team.getPlayers());
                 }

                 return true;
            } else if(args[0].equalsIgnoreCase("dbg_start") && p.hasPermission("arena.reload")) {
                 p.sendMessage("===arena_start===");
                try {
                    MinigamesDTools.getInstance().getArenaAPI().getArena(args[1]).startArena();
                } catch (ArenaAlreadyStartedException e) {
                    e.printStackTrace();
                }

                return true;
             }

             if(args[0].equalsIgnoreCase("dbg_stop") && p.hasPermission("arena.reload")) {
                 p.sendMessage("===arena_stop===");
                 p.sendMessage("stopped" + args[1]);
                 MinigamesDTools.getInstance().getArenaAPI().getArena(args[1]).forceDisable();

                 return true;
             }

             if(args[0].equalsIgnoreCase("dbg_arenas") && p.hasPermission("arena.reload")) {
                 p.sendMessage("===dbg_arenas===");
                 for(ArenaBase arena : MinigamesDTools.getInstance().getArenaAPI().getArenas()) {
                     p.sendMessage("- " + arena.getName() + " -> " + arena.isEnabled());
                 }
                 return true;
             }

             if(args[0].equalsIgnoreCase("leave") && p.hasPermission("arena.leave")) {
                 MinigamesDTools.getInstance().getArenaAPI().arenaLeaveRequest(p);

                 return true;
             }

            p.sendMessage("Wrong command");
            return true;
         }
      }

      return false;
   }

}
