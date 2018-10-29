package com.gmail.borlandlp.minigamesdtools.arena.localevent;

import org.bukkit.entity.Player;

@ArenaEventHandler
public class ArenaPlayerLeaveLocalEvent extends ArenaEvent {
    private Player player;

    public ArenaPlayerLeaveLocalEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public String toString() {
        return "{Event:" + this.getClass().getSimpleName() + " Player" + this.getPlayer() + "}";
    }
}
