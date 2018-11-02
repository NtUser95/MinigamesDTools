package com.gmail.borlandlp.minigamesdtools.arena.gui.providers;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaEventListener;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.*;

public class GUIListener implements ArenaEventListener {
    private GUIController guiController;

    public GUIListener(GUIController guiController) {
        this.guiController = guiController;
    }

    @ArenaEventHandler
    public void onPlayerDeath(ArenaPlayerDeathLocalEvent event) {
        Debug.print(Debug.LEVEL.NOTICE, this.getClass().getSimpleName() + "#" + event);
    }

    @ArenaEventHandler
    public void onPlayerKilled(ArenaPlayerKilledLocalEvent event) {
        Debug.print(Debug.LEVEL.NOTICE, this.getClass().getSimpleName() + "#" + event);
    }

    @ArenaEventHandler
    public void onPlayerLeave(ArenaPlayerLeaveLocalEvent event) {
        Debug.print(Debug.LEVEL.NOTICE, this.getClass().getSimpleName() + "#" + event);
    }

    @ArenaEventHandler
    public void onPlayerJoin(ArenaPlayerJoinLocalEvent event) {
        Debug.print(Debug.LEVEL.NOTICE, this.getClass().getSimpleName() + "#" + event);
    }
}
