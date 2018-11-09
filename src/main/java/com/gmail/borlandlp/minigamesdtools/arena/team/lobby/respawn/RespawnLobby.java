package com.gmail.borlandlp.minigamesdtools.arena.team.lobby.respawn;

import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Set;

public interface RespawnLobby extends ArenaPhaseComponent {
    void update();
    void addPlayer(Player player);
    Set<Player> getReadyPlayersToRespawn();
    Map<Player, Long> getWaitingPlayers();
    void removePlayer(Player player);
}
