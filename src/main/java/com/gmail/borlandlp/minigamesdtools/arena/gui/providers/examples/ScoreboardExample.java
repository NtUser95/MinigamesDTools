package com.gmail.borlandlp.minigamesdtools.arena.gui.providers.examples;

import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase;
import com.gmail.borlandlp.minigamesdtools.arena.gui.providers.GUIProvider;
import com.gmail.borlandlp.minigamesdtools.arena.team.TeamProvider;
import me.johnnykpl.scoreboardwrapper.ScoreboardWrapper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ScoreboardExample extends GUIProvider {
    private ScoreboardWrapper scoreboardWrapper;

    public ScoreboardExample(ArenaBase arena) {
        super(arena);
    }

    public ScoreboardExample() {}

    @Override
    public void onInit() {
        this.scoreboardWrapper = new ScoreboardWrapper("Title");
        this.scoreboardWrapper.addLine("example text");
        this.scoreboardWrapper.addBlankSpace();
        this.scoreboardWrapper.addLine("example text 2");
    }

    @Override
    public void beforeGameStarting() {

    }

    @Override
    public void gameEnded() {
        for(TeamProvider team : this.getArena().getTeamController().getTeams()) {
            for (Player player : team.getPlayers()) {
                if (player != null) {
                    player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                }
            }
        }
    }

    @Override
    public void update() {
        for(TeamProvider team : this.getArena().getTeamController().getTeams()) {
            for (Player player : team.getPlayers()) {
                if (player != null) {
                    player.setScoreboard(this.scoreboardWrapper.getScoreboard());
                }
            }
        }
    }

    @Override
    public void beforeRoundStarting() {

    }

    @Override
    public void onRoundEnd() {

    }
}
