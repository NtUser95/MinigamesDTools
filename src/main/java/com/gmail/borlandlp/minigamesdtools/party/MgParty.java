package com.gmail.borlandlp.minigamesdtools.party;

import com.gmail.borlandlp.minigamesdtools.party.event.PartyChangeLeaderEvent;
import com.gmail.borlandlp.minigamesdtools.party.event.PartyPlayerJoinedEvent;
import com.gmail.borlandlp.minigamesdtools.party.event.PartyPlayerKickedEvent;
import com.gmail.borlandlp.minigamesdtools.party.event.PartyPlayerLeaveEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MgParty implements Party {
    private Map<String, Player> members = new HashMap<>();
    private Player leader;
    private int maxSize = 2;

    public MgParty(Player leader) {
        this.members.put(leader.getName(), leader);
        this.leader = leader;
    }

    public void addPlayer(Player player) {
        this.members.put(player.getName(), player);
        Bukkit.getPluginManager().callEvent(new PartyPlayerJoinedEvent(this, player));
    }

    public void removePlayer(Player player) {
        if(this.members.remove(player.getName()) != null) {
            Bukkit.getPluginManager().callEvent(new PartyPlayerLeaveEvent(this, player));
        }
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(this.members.values());
    }

    public void setLeader(Player player) {
        if(this.members.containsKey(player.getName())) {
            Bukkit.getPluginManager().callEvent(new PartyChangeLeaderEvent(this, this.leader, player));
            this.leader = player;
        }
    }

    public Player getLeader() {
        return this.leader;
    }

    public void broadcast(String message) {
        for(Player player : this.getPlayers()) {
            player.sendMessage(message);
        }
    }

    @Override
    public int getMaxSize() {
        return this.maxSize;
    }

    @Override
    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public void kickPlayer(Player player) {
        if(!this.members.containsKey(player.getName())) {
            return;
        }

        this.members.remove(player.getName());
        Bukkit.getPluginManager().callEvent(new PartyPlayerKickedEvent(this, player));
        player.sendMessage("{kicked_from_party_msg}");
    }

    public String toString() {
        String memebrs = this.members.keySet().stream().map(Object::toString).collect(Collectors.joining("|"));
        return "{Party leader=" + this.leader.getName() + ", members=" + memebrs + ", maxSize=" + this.maxSize + "}";
    }
}
