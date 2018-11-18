package com.gmail.borlandlp.minigamesdtools.arena.team;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaEventListener;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.*;
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.ArenaLobby;
import org.bukkit.entity.Player;

public class TeamListener implements ArenaEventListener {
    private TeamController teamController;

    public TeamListener(TeamController teamController) {
        this.teamController = teamController;
    }

    @ArenaEventHandler(
            ignoreCancelled = true,
            priority = ArenaEventPriority.LOWEST
    )
    public void onPlayerDeath(ArenaPlayerDeathLocalEvent event) {
        this.handlePlayerDeath(event.getPlayer());
        Debug.print(Debug.LEVEL.NOTICE, this.getClass().getSimpleName() + "#" + event);
    }

    @ArenaEventHandler(
            ignoreCancelled = true,
            priority = ArenaEventPriority.LOWEST
    )
    public void onPlayerKilled(ArenaPlayerKilledLocalEvent event) {
        this.handlePlayerDeath(event.getPlayer());
        Debug.print(Debug.LEVEL.NOTICE, this.getClass().getSimpleName() + "#" + event);
    }

    private void handlePlayerDeath(Player p) {
        TeamProvider teamProvider = this.teamController.getTeamOf(p);
        ArenaPlayerRespawnRequestLocalEvent arenaPlayerRespawnRequestLocalEvent = new ArenaPlayerRespawnRequestLocalEvent(teamProvider, p);
        teamProvider.getArena().getEventAnnouncer().announce(arenaPlayerRespawnRequestLocalEvent);
        if(!arenaPlayerRespawnRequestLocalEvent.isCancelled()) {
            if(((ArenaLobby)teamProvider.getRespawnLobby()).isEnabled()) {
                teamProvider.movePlayerTo((ArenaLobby) teamProvider.getRespawnLobby(), p);
            } else {
                teamProvider.spawn(p);
            }
        } else {
            teamProvider.setSpectate(p, true);
        }
    }

    @ArenaEventHandler(
            ignoreCancelled = true,
            priority = ArenaEventPriority.LOWEST
    )
    public void onPlayerLeave(ArenaPlayerLeaveLocalEvent event) {
        Player player = event.getPlayer();
        TeamProvider team = this.teamController.getTeamOf(player);
        if(team != null) {
            player.getInventory().setArmorContents(null);
            player.getInventory().clear();
            team.removePlayer(player);
        }
    }

    @ArenaEventHandler(
            ignoreCancelled = true,
            priority = ArenaEventPriority.LOWEST
    )
    public void onPlayerJoin(ArenaPlayerJoinLocalEvent event) {
        event.getTeam().addPlayer(event.getPlayer());
    }
}
