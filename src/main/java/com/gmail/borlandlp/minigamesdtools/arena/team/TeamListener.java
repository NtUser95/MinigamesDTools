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
        TeamProvider teamProvider = this.teamController.getTeamOf(event.getPlayer());
        ArenaPlayerRespawnLocalEvent arenaPlayerRespawnLocalEvent = new ArenaPlayerRespawnLocalEvent(teamProvider, event.getPlayer());
        teamProvider.getArena().getEventAnnouncer().announce(arenaPlayerRespawnLocalEvent);
        if(!arenaPlayerRespawnLocalEvent.isCancelled()) {
            if(((ArenaLobby)teamProvider.getRespawnLobby()).isEnabled()) {
                teamProvider.movePlayerTo((ArenaLobby) teamProvider.getRespawnLobby(), event.getPlayer());
            } else {
                teamProvider.spawn(event.getPlayer());
            }
        } else {
            teamProvider.setSpectate(event.getPlayer(), true);
        }

        Debug.print(Debug.LEVEL.NOTICE, this.getClass().getSimpleName() + "#" + event);
    }

    @ArenaEventHandler(
            ignoreCancelled = true,
            priority = ArenaEventPriority.LOWEST
    )
    public void onPlayerKilled(ArenaPlayerKilledLocalEvent event) {
        TeamProvider teamProvider = this.teamController.getTeamOf(event.getPlayer());
        ArenaPlayerRespawnLocalEvent arenaPlayerRespawnLocalEvent = new ArenaPlayerRespawnLocalEvent(teamProvider, event.getPlayer());
        teamProvider.getArena().getEventAnnouncer().announce(arenaPlayerRespawnLocalEvent);
        if(!arenaPlayerRespawnLocalEvent.isCancelled()) {
            if(((ArenaLobby)teamProvider.getRespawnLobby()).isEnabled()) {
                teamProvider.movePlayerTo((ArenaLobby) teamProvider.getRespawnLobby(), event.getPlayer());
            } else {
                teamProvider.spawn(event.getPlayer());
            }
        } else {
            teamProvider.setSpectate(event.getPlayer(), true);
        }

        Debug.print(Debug.LEVEL.NOTICE, this.getClass().getSimpleName() + "#" + event);
    }

    @ArenaEventHandler(
            ignoreCancelled = true,
            priority = ArenaEventPriority.LOWEST
    )
    public void onPlayerLeave(ArenaPlayerLeaveLocalEvent event) {
        System.out.println(this.getClass().getSimpleName() + "#" + event);
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
        System.out.println(this.getClass().getSimpleName() + "#" + event);
        event.getTeam().addPlayer(event.getPlayer());
    }

    /*
    @ArenaEventHandler(
            ignoreCancelled = true,
            priority = ArenaEventPriority.LOWEST
    )
    public void onPlayerRespawn(ArenaPlayerRespawnLocalEvent event) {
        if(this.test.contains(event.getPlayer())) {
            event.setCancelled(true);
            Debug.print(Debug.LEVEL.NOTICE, "Revoke respawn for " + event.getPlayer().getName());
        } else {
            this.test.add(event.getPlayer());
        }
    }
    */
}
