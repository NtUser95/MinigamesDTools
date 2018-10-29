package com.gmail.borlandlp.minigamesdtools.arena.team.lobby;

import com.gmail.borlandlp.minigamesdtools.arena.team.TeamProvider;
import org.bukkit.Location;

public abstract class ArenaLobby {
    TeamProvider teamProvider;
    Location spawnPoint;

    public void setSpawnPoint(Location spawnPoint) {
        this.spawnPoint = spawnPoint;
    }

    public Location getSpawnPoint() {
        return spawnPoint;
    }

    public TeamProvider getTeamProvider() {
        return teamProvider;
    }

    public void setTeamProvider(TeamProvider teamProvider) {
        this.teamProvider = teamProvider;
    }
}
