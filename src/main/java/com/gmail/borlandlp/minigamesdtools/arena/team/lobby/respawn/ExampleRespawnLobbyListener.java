package com.gmail.borlandlp.minigamesdtools.arena.team.lobby.respawn;

import com.gmail.borlandlp.minigamesdtools.arena.ArenaEventListener;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.*;

public class ExampleRespawnLobbyListener implements ArenaEventListener {
    private RespawnLobby lobby;

    public ExampleRespawnLobbyListener(RespawnLobby l) {
        this.lobby = l;
    }

    @ArenaEventHandler(
            ignoreCancelled = true,
            priority = ArenaEventPriority.HIGH
    )
    public void onPlayerDamage(ArenaPlayerDamagedLocalEvent event) {
        if(this.lobby.getWaitingPlayers().containsKey(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @ArenaEventHandler(
            ignoreCancelled = true,
            priority = ArenaEventPriority.HIGH
    )
    public void onPlayerKilled(ArenaPlayerDeathLocalEvent event) {
        if(this.lobby.getWaitingPlayers().containsKey(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @ArenaEventHandler(
            ignoreCancelled = true,
            priority = ArenaEventPriority.HIGH
    )
    public void onPlayerKilled(ArenaPlayerKilledLocalEvent event) {
        if(this.lobby.getWaitingPlayers().containsKey(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
}