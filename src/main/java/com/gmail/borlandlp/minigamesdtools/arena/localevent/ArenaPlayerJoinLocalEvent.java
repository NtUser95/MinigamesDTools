package com.gmail.borlandlp.minigamesdtools.arena.localevent;

import com.gmail.borlandlp.minigamesdtools.arena.team.TeamProvider;
import org.bukkit.entity.Player;

@ArenaEventHandler
public class ArenaPlayerJoinLocalEvent extends ArenaEvent {
    private Player player;
    private TeamProvider team;

    public ArenaPlayerJoinLocalEvent(Player player, TeamProvider team) {
        this.player = player;
        this.team = team;
    }

    public Player getPlayer() {
        return player;
    }

    public TeamProvider getTeam() {
        return team;
    }

    public String toString() {
        return "{Event:" + this.getClass().getSimpleName() + " Player" + this.getPlayer() + " TeamProvider:" + this.getTeam() +"}";
    }
}
