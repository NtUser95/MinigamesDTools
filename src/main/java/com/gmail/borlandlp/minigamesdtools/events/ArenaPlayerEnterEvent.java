package com.gmail.borlandlp.minigamesdtools.events;

import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase;
import org.bukkit.entity.Player;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ArenaPlayerEnterEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private ArenaBase arenaBase;
    private Player player;

    public ArenaPlayerEnterEvent(ArenaBase arenaBase, Player p) {
        this.arenaBase = arenaBase;
        this.player = p;
    }

    public ArenaBase getArena() {
        return this.arenaBase;
    }

    public Player getPlayer() {
        return this.player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
