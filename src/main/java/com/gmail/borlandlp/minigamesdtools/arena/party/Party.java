package com.gmail.borlandlp.minigamesdtools.arena.party;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Party {
    protected Map<String, Player> members;
    protected Player leader;

    public Party(Player leader) {
        this.members = new HashMap<>();
        this.addPlayer(leader);
        this.setLeader(leader);
    }

    public void addPlayer(Player player) {
        this.members.put(player.getName(), player);
    }

    public void removePlayer(Player player) {
        this.members.remove(player);
    }

    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        for(String nickname : this.members.keySet()) {
            players.add(this.members.get(nickname));
        }
        return players;
    }

    public void setLeader(Player player) {
        if(this.members.containsKey(player.getName())) {
            this.leader = player;
        }
    }

    public Player getLeader() {
        return this.leader;
    }

    public boolean isLeader(Player player) {
        return player == this.getLeader();
    }

    public void broadcast(String message) {
        for(Player player : this.getPlayers()) {
            player.sendMessage(message);
        }
    }

    public int getSize() {
        return this.members.size();
    }

    public boolean containsByIP(String ip)
    {
        for(Player player : this.getPlayers()) {
            if(player.getAddress().getHostName().equals(ip)) {
                return true;
            }
        }

        return false;
    }

    public boolean containsByNickname(String nickname)
    {
        return this.members.containsKey(nickname);
    }

    public boolean allPlayersIsValid() {
        for(Player player : this.getPlayers()) {
            if(player == null || !player.isOnline() || player.isDead()) return false;
        }

        return true;
    }
}
