package com.gmail.borlandlp.minigamesdtools.party;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PartyListener implements Listener {
    private PartyManager manager;

    public PartyListener(PartyManager partyManager) {
        this.manager = partyManager;
    }

    @EventHandler
    public void pLeave(PlayerQuitEvent event) {
        this.handleDisconnect(event.getPlayer());
    }

    @EventHandler
    public void pLeave(PlayerKickEvent event) {
        this.handleDisconnect(event.getPlayer());
    }

    private void handleDisconnect(Player player) {
        Party party = this.manager.getPartyOf(player);
        if(party == null) {
            return;
        } else if(party.getPlayers().size() <= 1) {
            this.manager.removeParty(party);
            return;
        }

        if(party.getLeader() == player) {
            for (Player player_party : party.getPlayers()) {
                if(party.getLeader() != player_party) {
                    party.setLeader(player_party);
                    party.broadcast("[Party] new leader:" + player_party.getName());
                    break;
                }
            }
        }

        party.removePlayer(player);
    }
}
