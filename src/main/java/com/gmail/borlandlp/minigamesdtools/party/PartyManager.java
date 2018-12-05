package com.gmail.borlandlp.minigamesdtools.party;

import com.gmail.borlandlp.minigamesdtools.APIComponent;
import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.party.event.PartyCreateEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.Set;

public class PartyManager implements PartyAPI, APIComponent {
    private Listener pListener;
    private Set<Party> parties = new HashSet<>();

    @Override
    public void addParty(Party party) {
        Debug.print(Debug.LEVEL.NOTICE, "Add party, leader:" + party.getLeader().getName());
        this.parties.add(party);
    }

    @Override
    public void removeParty(Party party) {
        this.parties.remove(party);
    }

    @Override
    public Party getPartyOf(Player player) {
        return this.getPartyOf(player.getName());
    }

    @Override
    public Party getPartyOf(String player) {
        for (Party party : this.parties) {
            if(party.getPlayers().stream().anyMatch(p -> p.getName().equals(player))) {
                return party;
            }
        }

        return null;
    }

    @Override
    public Party createParty(Player leader) {
        Party party = new MgParty(leader);
        Bukkit.getPluginManager().callEvent(new PartyCreateEvent(party));
        return party;
    }

    @Override
    public void onLoad() {
        this.pListener = new PartyListener(this);
        Bukkit.getPluginManager().registerEvents(this.pListener, MinigamesDTools.getInstance());
    }

    @Override
    public void onUnload() {
        HandlerList.unregisterAll(this.pListener);
        this.pListener = null;
    }
}
