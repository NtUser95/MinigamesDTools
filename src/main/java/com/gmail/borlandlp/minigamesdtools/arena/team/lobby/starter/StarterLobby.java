package com.gmail.borlandlp.minigamesdtools.arena.team.lobby.starter;

import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent;
import org.bukkit.entity.Player;

import java.util.Set;

public interface StarterLobby extends ArenaPhaseComponent {
    boolean isEnabled();
    void setEnabled(boolean trueOrFalse);
    void addPlayer(Player player);
    void removePlayer(Player player);
    Set<Player> getPlayers();
}
