package com.gmail.borlandlp.minigamesdtools.party;

import org.bukkit.entity.Player;

import java.util.List;

public interface Party {
    void addPlayer(Player player);
    void removePlayer(Player player);
    List<Player> getPlayers();
    void setLeader(Player player);
    Player getLeader();
    void broadcast(String message);
    void setMaxSize(int size);
    int getMaxSize();
    void kickPlayer(Player player);
}
