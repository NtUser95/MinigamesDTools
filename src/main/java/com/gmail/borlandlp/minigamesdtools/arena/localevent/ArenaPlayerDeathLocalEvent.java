package com.gmail.borlandlp.minigamesdtools.arena.localevent;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

@ArenaEventHandler
public class ArenaPlayerDeathLocalEvent extends ArenaEvent implements Cancellable {
    private Player player;
    private boolean canceled;

    public ArenaPlayerDeathLocalEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
    @Override
    public boolean isCancelled() {
        return this.canceled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.canceled = b;
    }

    public String toString() {
        return "{Event:" + this.getClass().getSimpleName() + " Player" + this.getPlayer() + "}";
    }
}
