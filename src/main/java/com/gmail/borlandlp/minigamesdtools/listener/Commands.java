package com.gmail.borlandlp.minigamesdtools.listener;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase;
import com.gmail.borlandlp.minigamesdtools.arena.exceptions.ArenaAlreadyStartedException;
import com.gmail.borlandlp.minigamesdtools.arena.team.TeamProvider;
import com.gmail.borlandlp.minigamesdtools.config.exception.InvalidPathException;
import com.gmail.borlandlp.minigamesdtools.creator.DataProvider;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.Hotbar;
import com.gmail.borlandlp.minigamesdtools.party.Party;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Commands implements CommandExecutor {
   public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
      if(cmd.getName().equalsIgnoreCase("arena")) {

          // debug...
          Player player = (Player) sender;
        if(args[0].equalsIgnoreCase("lobby")) {
          MinigamesDTools.getInstance().getLobbyHubAPI().getLobbyByID("spawn_lobby").registerPlayer(player);
        } else if(args[0].equalsIgnoreCase("lobby_leave")) {
          MinigamesDTools.getInstance().getLobbyHubAPI().getLobbyByID("spawn_lobby").unregisterPlayer(player);
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
        } else if(args[0].equalsIgnoreCase("dbg_join") && player.hasPermission("arena.reload")) {

            MinigamesDTools.getInstance().getArenaAPI().arenaJoinRequest(args[1], player);
            player.sendMessage("dbg_join: " + args[1]);
            return true;

        } else if(args[0].equalsIgnoreCase("dbg_state")) {

            ArenaBase.STATE state = ArenaBase.STATE.valueOf(args[2]);
            MinigamesDTools.getInstance().getArenaAPI().getArena(args[1]).setState(state);
            player.sendMessage("dbg_state:" + args[1] + " " + MinigamesDTools.getInstance().getArenaAPI().getArena(args[1]));
            return true;

        } else if(args[0].equalsIgnoreCase("dbg_members")) {
             player.sendMessage("===dbg_teams===");
             for(TeamProvider team : MinigamesDTools.getInstance().getArenaAPI().getArena(args[1]).getTeamController().getTeams()) {
                 player.sendMessage("dbg_teams->" + args[1] + ":" + team.getName() + "#" + team.getPlayers());
             }

             return true;
        } else if(args[0].equalsIgnoreCase("dbg_start")) {
             player.sendMessage("===arena_start===");
            try {
                MinigamesDTools.getInstance().getArenaAPI().getArena(args[1]).startArena();
            } catch (ArenaAlreadyStartedException e) {
                e.printStackTrace();
            }

            return true;
        } else if(args[0].equalsIgnoreCase("hotbar")) {
              try {
                  Hotbar hotbar = MinigamesDTools.getInstance().getHotbarCreatorHub().createHotbar("example_skyhotbar", new DataProvider());
                  MinigamesDTools.getInstance().getHotbarAPI().bindHotbar(hotbar, player);
              } catch (Exception e) {
                  e.printStackTrace();
              }
        } else if(args[0].equalsIgnoreCase("dbg_stop")) {
             player.sendMessage("===arena_stop===");
             player.sendMessage("stopped" + args[1]);
             MinigamesDTools.getInstance().getArenaAPI().getArena(args[1]).forceDisable();

             return true;
        } else if(args[0].equalsIgnoreCase("leave")) {
             MinigamesDTools.getInstance().getArenaAPI().arenaLeaveRequest(player);

             return true;
        } else if(args[0].equalsIgnoreCase("party_create")) {
            Party party = MinigamesDTools.getInstance().getPartyAPI().createParty(player);
            MinigamesDTools.getInstance().getPartyAPI().addParty(party);

            return true;
        } else if(args[0].equalsIgnoreCase("party_join")) {
            MinigamesDTools.getInstance().getPartyAPI().getPartyOf(args[1]).addPlayer(player);
            return true;
        } else if(args[0].equalsIgnoreCase("party_members")) {
            System.out.print(MinigamesDTools.getInstance().getPartyAPI().getPartyOf(player));

            return true;
        }

        player.sendMessage("Wrong command");
        return true;
     }

      return false;
   }

}
