package com.gmail.borlandlp.minigamesdtools.arena.team.lobby;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.arena.team.TeamProvider;
import com.gmail.borlandlp.minigamesdtools.creator.DataProvider;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.Hotbar;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public abstract class ArenaLobby {
    protected TeamProvider teamProvider;
    protected Location spawnPoint;
    protected boolean enabled;
    protected boolean hotbarEnabled;
    protected String hotbarId;

    public boolean isHotbarEnabled() {
        return hotbarEnabled;
    }

    public void setHotbarEnabled(boolean hotbarEnabled) {
        this.hotbarEnabled = hotbarEnabled;
    }

    public void setHotbarId(String hotbarId) {
        this.hotbarId = hotbarId;
    }

    public Hotbar getHotbarFor(Player player) {
        Hotbar hotbar = null;
        try {
            hotbar = MinigamesDTools.getInstance().getHotbarCreatorHub().createHotbar(this.hotbarId, new DataProvider() {{
                this.set("player", player);
            }});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hotbar;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

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

    /*
    * TODO: Перевести на аглийский.
    * TeamProvider может запросить принудительное отключение игрока от лобби, если ему так потребуется.
    * В остальных случаях, лобби само решает, как ему распоряжаться с этим игроком.
    * */
    public abstract void forceReleasePlayer(Player p);

    public abstract void addPlayer(Player player);
}
