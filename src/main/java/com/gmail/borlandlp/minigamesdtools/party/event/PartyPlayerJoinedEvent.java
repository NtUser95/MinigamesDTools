package com.gmail.borlandlp.minigamesdtools.party.event;

import com.gmail.borlandlp.minigamesdtools.party.Party;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PartyPlayerJoinedEvent extends Event {
    private Party party;
    private Player player;
    private static final HandlerList handlers = new HandlerList();

    public PartyPlayerJoinedEvent(Party party, Player player) {
        this.party = party;
        this.player = player;
    }

    public Party getParty() {
        return party;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
