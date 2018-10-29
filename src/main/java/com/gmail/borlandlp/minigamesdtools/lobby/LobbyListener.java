package com.gmail.borlandlp.minigamesdtools.lobby;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.events.ArenaPlayerEnterEvent;
import com.gmail.borlandlp.minigamesdtools.events.ArenaPlayerQuitEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.logging.Level;

public class LobbyListener implements Listener {
    private LobbyHubController lobbyController;

    public LobbyListener(LobbyHubController l) {
        this.lobbyController = l;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPJoin(PlayerJoinEvent event) {
        if(this.lobbyController.isStartLobbyEnabled()) {
            if(this.lobbyController.getDefaultServerLobby() != null) {
                this.lobbyController.getDefaultServerLobby().registerPlayer(event.getPlayer());
            } else {
                MinigamesDTools.getInstance().getLogger().log(Level.WARNING, "Cant find default start_lobby with.");
            }
        }
    }

    @EventHandler
    public void onPLeaveArena(ArenaPlayerQuitEvent event) {
        ServerLobby serverLobby = MinigamesDTools.getInstance().getLobbyHubAPI().getLobbyByPlayer(event.getPlayer());
        if(serverLobby != null) {
            serverLobby.spawn(event.getPlayer());
            serverLobby.setPlayerArena(event.getPlayer(), null);
        } else {
            this.lobbyController.getDefaultServerLobby().registerPlayer(event.getPlayer());
        }
    }

    @EventHandler
    public void onPJoin(ArenaPlayerEnterEvent event) {
        ServerLobby serverLobby = MinigamesDTools.getInstance().getLobbyHubAPI().getLobbyByPlayer(event.getPlayer());
        if(serverLobby != null) {
            serverLobby.setPlayerArena(event.getPlayer(), event.getArena());
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerKick(PlayerKickEvent event) {
        ServerLobby serverLobby = MinigamesDTools.getInstance().getLobbyHubAPI().getLobbyByPlayer(event.getPlayer());
        if(serverLobby != null) {
            serverLobby.unregisterPlayer(event.getPlayer());
        }
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent event) {
        ServerLobby serverLobby = MinigamesDTools.getInstance().getLobbyHubAPI().getLobbyByPlayer(event.getPlayer());
        if(serverLobby != null) {
            serverLobby.unregisterPlayer(event.getPlayer());
        }
    }
}
