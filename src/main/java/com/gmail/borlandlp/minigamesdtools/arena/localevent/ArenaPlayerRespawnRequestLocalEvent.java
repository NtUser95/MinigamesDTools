package com.gmail.borlandlp.minigamesdtools.arena.localevent;

import com.gmail.borlandlp.minigamesdtools.arena.team.TeamProvider;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public class ArenaPlayerRespawnRequestLocalEvent extends ArenaEvent implements Cancellable {
    private Player player;
    private TeamProvider teamProvider;
    private boolean canceled;

    public ArenaPlayerRespawnRequestLocalEvent(TeamProvider t, Player p) {
        this.player = p;
        this.teamProvider = t;
    }

    public Player getPlayer() {
        return player;
    }

    public TeamProvider getTeamProvider() {
        return teamProvider;
    }

    @Override
    public boolean isCancelled() {
        return this.canceled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.canceled = b;
    }
}
