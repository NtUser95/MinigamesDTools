package com.gmail.borlandlp.minigamesdtools.arena.team;

import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent;
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.ArenaLobby;
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

    boolean addPlayer(Player player);
    boolean removePlayer(Player player);

    boolean isManageInventory();
    void setManageInventory(boolean b);
    boolean isManageArmor();
    void setManageArmor(boolean b);

    RespawnLobby getRespawnLobby();
    void setRespawnLobby(RespawnLobby l);
    SpectatorLobby getSpectatorLobby();
    void setSpectatorLobby(SpectatorLobby l);

    boolean movePlayerTo(ArenaLobby lobby, Player p);
    boolean movePlayerTo(TeamProvider team, Player p);

    void setSpectate(Player p, boolean trueOrFalse);
    Set<Player> getSpectators();
}
