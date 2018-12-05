package com.gmail.borlandlp.minigamesdtools.party.event;

import com.gmail.borlandlp.minigamesdtools.party.Party;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PartyRemoveEvent extends Event {
    private Party party;
    private static final HandlerList handlers = new HandlerList();

    public PartyRemoveEvent(Party party) {
        this.party = party;
    }

    public Party getParty() {
        return party;
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
