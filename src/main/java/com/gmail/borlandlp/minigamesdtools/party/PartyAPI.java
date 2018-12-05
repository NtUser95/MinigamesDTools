package com.gmail.borlandlp.minigamesdtools.party;

import org.bukkit.entity.Player;

public interface PartyAPI {
    void addParty(Party party);
    void removeParty(Party party);
    Party getPartyOf(Player player);
    Party getPartyOf(String player);
    Party createParty(Player leader);
}
