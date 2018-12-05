package com.gmail.borlandlp.minigamesdtools.arena;

import com.gmail.borlandlp.minigamesdtools.arena.exceptions.ArenaAlreadyAddedException;
import com.gmail.borlandlp.minigamesdtools.conditions.PlayerCheckResult;
import com.gmail.borlandlp.minigamesdtools.party.Party;
import org.bukkit.entity.Player;
import com.gmail.borlandlp.minigamesdtools.config.ConfigProvider;

import java.util.ArrayList;

public interface ArenaAPI {
    /**
     * Add the name of the arena, which can be created automatically
     * and loaded during the initialization of the MinigamesDTools
     * Arena configuration will be requested from {@link ConfigProvider#getEntity(String, String)}
     * @param arenaName - ArenaName
     */
    void registerToLoad(String arenaName);

    /**
     * Removes an arena from the automatic activation queue.
     * @param arenaName - ArenaName
     */
    void unregisterToLoad(String arenaName);

    /**
     * Ends the current gaming session of the arena and re-creates the arena for a new gaming session.
     * @param arenaName - ArenaName
     */
    void restartArena(String arenaName);

    /**
     * Registers an instance of the arena,
     * which has already been initialized and is ready to start a new gaming session with players.
     * @param arena - {@link ArenaBase} instance
     * @throws ArenaAlreadyAddedException - throws if another registered arena with the same name is found.
     */
    void addArena(ArenaBase arena) throws ArenaAlreadyAddedException;

    /**
     * Removes an arena from the list of available game arenas.
     * @param arenaName - ArenaName
     */
    void removeArena(String arenaName);

    /**
     * Look for the game arena where the player is located.
     * @param player
     * @return {@link Player} or null if player not found.
     */
    ArenaBase getArenaOf(Player player);

    /**
     * Look for the game arena where the player is located.
     * @param playerName - PlayerName
     * @return {@link Player} or null if player not found.
     */
    ArenaBase getArenaOf(String playerName);

    /**
     * Сase-sensitive search for a game arena by name.
     * @param arenaName - Arenaname
     * @return {@link ArenaBase}
     */
    ArenaBase getArena(String arenaName);

    /**
     * Returns a copy of all registered arenas.
     * @return {@link java.util.ArrayList<ArenaBase>}
     */
    ArrayList<ArenaBase> getArenas();

    /**
     * Returns a copy of all enabled arenas.
     * @return {@link java.util.ArrayList<ArenaBase>}
     */
    ArrayList<ArenaBase> getEnabledArenas();

    /**
     * Returns a copy of all disabled arenas.
     * @return {@link java.util.ArrayList<ArenaBase>}
     */
    ArrayList<ArenaBase> getDisabledArenas();

    /**
     * Sends a request to exit a player from his current gaming arena
     * @param player - Player
     */
    void arenaLeaveRequest(Player player);

    /**
     * Sends a single player entry request.
     * @param arenaName - arenaName
     * @param player - {@link Player}
     * @return - true if the player passed all the checks and successfully entered the game arena.
     */
    boolean arenaJoinRequest(String arenaName, Player player);

    /**
     * Sends a single player entry request.
     * @param arenaName - arenaName
     * @param party - {@link Party}
     * @return - true if the party passed all the checks and successfully entered the game arena.
     */
    boolean arenaJoinRequest(String arenaName, Party party);

    /**
     * Makes the arena inaccessible for players.
     * @param arenaName - ArenaName
     */
    void disableArena(String arenaName);

    /**
     * Makes the arena accessible for connections by players.
     * @param arenaName
     */
    void enableArena(String arenaName);

    /**
     * Check whether the player meets all the conditions of this gaming arena.
     * @param arenaBase - {@link ArenaBase}
     * @param player - {@link Player}
     * @return {@link PlayerCheckResult}
     */
    PlayerCheckResult playerCanJoin(ArenaBase arenaBase, Player player);
}
