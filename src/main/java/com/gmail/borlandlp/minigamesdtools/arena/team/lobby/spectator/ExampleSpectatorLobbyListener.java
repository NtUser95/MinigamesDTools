package com.gmail.borlandlp.minigamesdtools.arena.team.lobby.spectator;

import com.gmail.borlandlp.minigamesdtools.arena.ArenaEventListener;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaEventHandler;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerDamagedLocalEvent;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerDeathLocalEvent;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerKilledLocalEvent;
import com.gmail.borlandlp.minigamesdtools.events.ArenaRequestSpectatePlayer2Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ExampleSpectatorLobbyListener implements ArenaEventListener, Listener {
    private SpectatorLobby lobby;

    public ExampleSpectatorLobbyListener(SpectatorLobby l) {
        this.lobby = l;
    }

    @EventHandler
    public void onSpectateReq(ArenaRequestSpectatePlayer2Player event) {
        event.getPlayerInvoker().setSpectatorTarget(event.getPlayerTarget());
    }

    @ArenaEventHandler
    public void onPlayerDamage(ArenaPlayerDamagedLocalEvent event) {
        if(this.lobby.getPlayers().contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @ArenaEventHandler
    public void onPlayerKilled(ArenaPlayerDeathLocalEvent event) {
        if(this.lobby.getPlayers().contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @ArenaEventHandler
    public void onPlayerKilled(ArenaPlayerKilledLocalEvent event) {
        if(this.lobby.getPlayers().contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
}
