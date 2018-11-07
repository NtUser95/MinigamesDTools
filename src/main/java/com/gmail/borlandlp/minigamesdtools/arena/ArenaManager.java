package com.gmail.borlandlp.minigamesdtools.arena;

import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

import com.gmail.borlandlp.minigamesdtools.APIComponent;
import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.arena.exceptions.ArenaAlreadyInitializedException;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerJoinLocalEvent;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerLeaveLocalEvent;
import com.gmail.borlandlp.minigamesdtools.arena.team.TeamController;
import com.gmail.borlandlp.minigamesdtools.arena.team.TeamProvider;
import com.gmail.borlandlp.minigamesdtools.conditions.PlayerCheckResult;
import com.gmail.borlandlp.minigamesdtools.config.ConfigEntity;
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath;
import com.gmail.borlandlp.minigamesdtools.creator.DataProvider;
import com.gmail.borlandlp.minigamesdtools.events.ArenaPlayerEnterEvent;
import com.gmail.borlandlp.minigamesdtools.events.ArenaPlayerQuitEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ArenaManager implements APIComponent, ArenaAPI {
    private Map<String, ArenaBase> mArenas = new Hashtable<>();
    private List<String> reg2Load = new ArrayList<>();

    @Override
    public void onLoad() {
        ArenaBase arena = null;
        for(ConfigEntity configEntity : MinigamesDTools.getInstance().getConfigProvider().getPoolContents(ConfigPath.ARENA_FOLDER)) {
            this.registerToLoad(configEntity.getID());
        }

        for (String arenaName : this.reg2Load) {
            try {
                arena = MinigamesDTools.getInstance().getArenaCreatorHub().createArena(arenaName, new DataProvider());
                try {
                    arena.initializeComponents();
                } catch (ArenaAlreadyInitializedException e) {
                    e.printStackTrace();
                    continue;
                }
                this.addArena(arena);
                Debug.print(Debug.LEVEL.NOTICE, "successful load arena '" + arenaName + "'");
            } catch(Exception ex) {
                MinigamesDTools.getInstance().getLogger().log(Level.WARNING, " failed to load arena " + arenaName);
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onUnload() {
        for(ArenaBase arena : this.getEnabledArenas()) {
            arena.forceDisable();
        }
    }

    @Override
    public void registerToLoad(String arenaName) {
        this.reg2Load.add(arenaName);
    }

    @Override
    public void unregisterToLoad(String arenaName) {
        this.reg2Load.remove(arenaName);
    }

    @Override
    public void restartArena(String name) {
        if(!this.mArenas.containsKey(name)) {
            return;
        } else if(this.mArenas.get(name).getState() != ArenaBase.STATE.ENDED) {
            this.mArenas.get(name).gameEnded(false);
        }

        ArenaBase oldInstance = this.mArenas.get(name);
        this.mArenas.remove(name);
        ArenaBase newInstance = null;
        try {
            newInstance = MinigamesDTools.getInstance().getArenaCreatorHub().createArena(name, new DataProvider());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        try {
            newInstance.initializeComponents();
        } catch (ArenaAlreadyInitializedException e) {
            e.printStackTrace();
            return;
        }
        this.mArenas.put(name, newInstance);
        Debug.print(Debug.LEVEL.NOTICE, "Reload arena '" + name + "', oldInstance:" + oldInstance + " # newInstance:" + newInstance);
    }

    @Override
    public void addArena(ArenaBase arena) throws Exception {
       for(ArenaBase qArena : this.getArenas()) {
           if(qArena.getName().equalsIgnoreCase(arena.getName())) {
               throw new Exception("error adding arena '" + arena.getName() + "', reason: arena with a similar name is already registered.");
           }
       }

       this.mArenas.put(arena.getName(), arena);
   }

    @Override
    public void removeArena(String arenaName) {
        this.mArenas.remove(arenaName);
   }

    public ArenaBase getArenaOf(Player player) {
        for (ArenaBase arenaBase : this.getEnabledArenas()) {
            if(arenaBase.getTeamController().getTeamOf(player) != null) return arenaBase;
        }

        return null;
    }

    @Override
    public ArenaBase getArenaOf(String playerName) {
        for (ArenaBase arenaBase : this.getEnabledArenas()) {
            if(arenaBase.getTeamController().getTeamOf(playerName) != null) return arenaBase;
        }

        return null;
    }

   public ArenaBase getArena(String arenaName) {
       return this.mArenas.get(arenaName);
   }

   public ArrayList<ArenaBase> getArenas() {
      return new ArrayList<>(this.mArenas.values());
   }

   public ArrayList<ArenaBase> getEnabledArenas() {
       return this.getArenas().stream().filter(ArenaBase::isEnabled).collect(Collectors.toCollection(ArrayList::new));
   }

   public ArrayList<ArenaBase> getDisabledArenas() {
      return this.getArenas().stream().filter(x -> !x.isEnabled()).collect(Collectors.toCollection(ArrayList::new));
   }

    public void arenaLeaveRequest(Player player) {
        ArenaBase arena = this.getArenaOf(player);
        if(arena != null) {
            arena.getEventAnnouncer().announce(new ArenaPlayerLeaveLocalEvent(player));
        } else {
            Debug.print(Debug.LEVEL.NOTICE, "Cant find arena for Player[name:" + player.getName() + "]");
            return;
        }

        ArenaPlayerQuitEvent event = new ArenaPlayerQuitEvent(arena, player);
        Bukkit.getPluginManager().callEvent(event);

        if(arena.getState() == ArenaBase.STATE.DELAYED_START) {
            if(arena.getTeamController().countCurrentPlayers() < arena.getMinPlayersToStart()) {
                arena.cancelDelayedStartArena();
            }
        }
    }

   public boolean arenaJoinRequest(String arenaName, Player player) {
        ArenaBase arenaBase = this.getArena(arenaName);
        if(arenaBase == null) {
            player.sendMessage(MinigamesDTools.getPrefix() + " Арены [" + arenaName + "] не существует.");
            return false;
        }

        TeamController teamControl = arenaBase.getTeamController();
        if(teamControl.getTeams().stream().noneMatch(t -> t.containsFreeSlots(1))) {
            player.sendMessage(" Арена " + arenaName + " заполнена");
            return false;
        }

        // TODO: Приделать конвертацию ID в Broadcaster
        PlayerCheckResult result = this.playerCanJoin(arenaBase, player);
        if(result.getResult() == PlayerCheckResult.CheckResult.DENIED) {
            result.getErrId().forEach(player::sendMessage);
            return false;
        }

        TeamProvider team = teamControl.getTeams().stream().filter(t -> t.containsFreeSlots(1)).findFirst().get();

        arenaBase.getEventAnnouncer().announce(new ArenaPlayerJoinLocalEvent(player, team));
        Bukkit.getPluginManager().callEvent(new ArenaPlayerEnterEvent(arenaBase, player));

        if(arenaBase.getTeamController().countCurrentPlayers() >= arenaBase.getMinPlayersToStart()) {
            arenaBase.delayedStartArena();
        }

        return true;
   }

   public void disableArena(String arenaName) {
       ArenaBase arena = this.getArena(arenaName);
       if(arena != null) {
          arena.setEnabled(false);
          Bukkit.getServer().broadcastMessage("[MinigamesDTools] Выключена арена " + arenaName);
       }
   }

   public void enableArena(String arenaName) {
        ArenaBase arena = this.getArena(arenaName);
        if(arena != null) {
            arena.setEnabled(true);
            Bukkit.getServer().broadcastMessage("[MinigamesDTools] Запущена арена " + arenaName);
        }
   }

    @Override
    public PlayerCheckResult playerCanJoin(ArenaBase arenaBase, Player player) {
        return arenaBase.getJoinConditionsChain().check(player);
    }
}
