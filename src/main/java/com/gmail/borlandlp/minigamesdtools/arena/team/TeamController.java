package com.gmail.borlandlp.minigamesdtools.arena.team;

import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaPlayersRelative;
import org.bukkit.entity.Player;

import java.util.*;

public class TeamController implements ArenaPhaseComponent {
	private ArrayList<TeamProvider> teams = new ArrayList<>();
	private TeamListener teamListener;
	private ArenaBase arena;

	public TeamController(ArenaBase arena) {
		this.arena = arena;
	}

	public ArenaBase getArena() {
		return arena;
	}

	public void addTeam(TeamProvider team) {
		this.teams.add(team);
	}

	public void removeTeam(TeamProvider team) {
		this.teams.remove(team);
	}

	public ArrayList<TeamProvider> getTeams() {
		return this.teams;
	}

	@Override
	public void onInit() {
		this.teamListener = new TeamListener(this);
		try {
			this.getArena().getEventAnnouncer().register(this.teamListener);
		} catch (Exception e) {
			e.printStackTrace();
		}


		for(TeamProvider team : this.getTeams()) {
			this.getArena().getPhaseComponentController().register(team);
		}
	}

    @Override
	public void beforeGameStarting() {

	}

	@Override
	public void gameEnded() {
        this.getArena().getEventAnnouncer().unregister(this.teamListener);
	}

	@Override
	public void update() {

	}

	@Override
	public void beforeRoundStarting() {

	}

	@Override
	public void onRoundEnd() {

	}

	public int countCurrentPlayers() {
		return this.getTeams().stream().mapToInt(t -> t.getPlayers().size()).sum();
	}

	public TeamProvider getTeam(String teamName) {
	    if(this.getTeams().stream().anyMatch(team -> team.getName().equals(teamName))) {
            return this.getTeams().stream().filter(team -> team.getName().equals(teamName)).findFirst().get();
        } else {
	        return null;
        }
	}

	public TeamProvider getTeamOf(String nickname) {
		for(TeamProvider team : this.getTeams()) {
			for (Player player : team.getPlayers()) {
				if(player.getName().equals(nickname)) return team;
			}
		}

		return null;
	}

	public TeamProvider getTeamOf(Player player) {
		return this.getTeamOf(player.getName());
	}

	public ArenaPlayersRelative getPlayersRelative(Player player1, Player player2) {
		TeamProvider team1 = this.getTeamOf(player1);
		TeamProvider team2 = this.getTeamOf(player2);

		if(team1 == null || team2 == null) {
			return ArenaPlayersRelative.ENEMY;
		} else if(team1 == team2) {
			return ArenaPlayersRelative.TEAMMATE;
		} else {
			return ArenaPlayersRelative.ENEMY;
		}
	}
}