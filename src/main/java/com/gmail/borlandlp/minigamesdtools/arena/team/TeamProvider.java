package com.gmail.borlandlp.minigamesdtools.arena.team;

import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent;
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.respawn.RespawnLobby;
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.spectator.SpectatorLobby;
import org.bukkit.entity.Player;

import java.util.Set;

public interface TeamProvider extends ArenaPhaseComponent {
    String getName();
    boolean containsFreeSlots(int forAmountPlayers);
    void spawn(Player player);

    void setArena(ArenaBase arena);
    ArenaBase getArena();
    Set<Player> getPlayers();

    void addPlayer(Player player);
    void removePlayer(Player player);

    boolean isManageInventory();
    void setManageInventory(boolean b);
    boolean isManageArmor();
    void setManageArmor(boolean b);

    boolean respawnLobbyEnabled();
    void setRespawnLobbyEnabled(Boolean b);
    RespawnLobby getRespawnLobby();
    void moveToRespawn(Player p);

    void setSpectatorLobby(SpectatorLobby l);
    SpectatorLobby getSpectatorLobby();
    void setSpectate(Player p, boolean trueOrFalse);
    boolean isSpectating(Player p);
}
