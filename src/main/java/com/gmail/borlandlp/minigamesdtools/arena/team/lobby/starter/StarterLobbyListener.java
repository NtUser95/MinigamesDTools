package com.gmail.borlandlp.minigamesdtools.arena.team.lobby.starter;

import com.gmail.borlandlp.minigamesdtools.arena.ArenaEventListener;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaEventHandler;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaEventPriority;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerJoinLocalEvent;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerLeaveLocalEvent;

public class StarterLobbyListener implements ArenaEventListener {
    private StarterLobby lobby;

    public StarterLobbyListener(StarterLobby l) {
        this.lobby = l;
    }

    @ArenaEventHandler(priority = ArenaEventPriority.HIGHEST)
    public void onPJoin(ArenaPlayerJoinLocalEvent event) {
        if(this.lobby.isEnabled()) {
            this.lobby.addPlayer(event.getPlayer());
        }
    }

    @ArenaEventHandler(priority = ArenaEventPriority.HIGHEST)
    public void onPLeave(ArenaPlayerLeaveLocalEvent event) {
        if(this.lobby.isEnabled()) {
            this.lobby.removePlayer(event.getPlayer());
        }
    }
}
