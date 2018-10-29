package com.gmail.borlandlp.minigamesdtools.arena.gui.providers;

import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent;

public abstract class GUIProvider implements ArenaPhaseComponent {
    private ArenaBase arena;

    public GUIProvider(ArenaBase arenaBase) {
        this.arena = arenaBase;
    }

    public GUIProvider() {}

    public ArenaBase getArena() {
        return arena;
    }

    public void setArena(ArenaBase arena) {
        this.arena = arena;
    }
}
