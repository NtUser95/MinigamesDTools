package com.gmail.borlandlp.minigamesdtools.arena;

public abstract class ArenaComponent {
    protected ArenaBase arena;

    public void setArena(ArenaBase arena) {
        this.arena = arena;
    }

    public ArenaBase getArena() {
        return arena;
    }
}
