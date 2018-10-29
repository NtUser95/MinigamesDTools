package com.gmail.borlandlp.minigamesdtools.arena.team.lobby.spectator;

import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent;
import org.bukkit.entity.Player;

import java.util.Set;

public interface SpectatorLobby extends ArenaPhaseComponent {
    void addPlayer(Player player);
    void removePlayer(Player player);
    Set<Player> getPlayers();
}
