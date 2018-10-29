package com.gmail.borlandlp.minigamesdtools.lobby;

import org.bukkit.entity.Player;

public interface LobbyServerAPI {
    ServerLobby getLobbyByPlayer(Player p);
    ServerLobby getLobbyByID(String id);
    void register(ServerLobby l);
    void unregister(ServerLobby l);
}
