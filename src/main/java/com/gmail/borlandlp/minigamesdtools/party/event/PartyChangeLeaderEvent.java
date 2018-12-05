package com.gmail.borlandlp.minigamesdtools.party.event;

import com.gmail.borlandlp.minigamesdtools.party.Party;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PartyChangeLeaderEvent extends Event {
    private Party party;
    private Player oldLeader;
    private Player newLeader;
    private static final HandlerList handlers = new HandlerList();

    public PartyChangeLeaderEvent(Party party, Player oldLeader, Player newLeader) {
        this.party = party;
        this.oldLeader = oldLeader;
        this.newLeader = newLeader;
    }

    public Party getParty() {
        return party;
    }

    public Player getOldLeader() {
        return oldLeader;
    }

    public Player getNewLeader() {
        return newLeader;
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
