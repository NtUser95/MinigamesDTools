package com.gmail.borlandlp.minigamesdtools.arena;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public interface ArenaAPI {
    void restartArena(String name);
    void addArena(ArenaBase arena) throws Exception;
    void removeArena(String arenaName);
    ArenaBase getArenaOf(Player player);
    ArenaBase getArenaOf(String playerName);
    ArenaBase getArena(String arenaName);
    ArrayList<ArenaBase> getArenas();
    ArrayList<ArenaBase> getEnabledArenas();
    ArrayList<ArenaBase> getDisabledArenas();
    void arenaLeaveRequest(Player player);
    boolean arenaJoinRequest(String arenaName, Player player);
    void disableArena(String arenaName);
    void enableArena(String arenaName);
}
