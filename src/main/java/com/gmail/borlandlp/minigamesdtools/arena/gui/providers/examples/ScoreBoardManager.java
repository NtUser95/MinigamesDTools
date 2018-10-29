package com.gmail.borlandlp.minigamesdtools.arena.gui.providers.examples;

public class ScoreBoardManager {
   /* private ArenaBase arena = null;

    public ScoreBoardManager(ArenaBase arena) {
        this.arena = arena;
    }

    public ArenaBase getArena() {
        return arena;
    }

    public Scoreboard getScoreboard() {
        ScoreboardWrapper sc_wrapper = new ScoreboardWrapper("== Статистика ==");
        for(ExampleTeam team : this.getArena().getTeamController().getTeams()) {
            if(team.isSpectatorsTeam()) {
                continue;
            }

            String text = team.getName() + " => Побед: " + team.wins + " [Уб:" + team.getKills() + "|Cм:" + team.getDeaths() + "/" + team.getMaxDeaths() + "]";
            sc_wrapper.addLine(text);
        }
        sc_wrapper.addBlankSpace();
        sc_wrapper.addLine("Раунд: " + this.getArena().getCurrentRound() + "/" + this.getArena().getMaxRounds());
        sc_wrapper.addLine("Ост. времени: " + this.getArena().getTimeLeft());

        return sc_wrapper.getScoreboard();
    }

    @Override
    public void update() {
        Scoreboard scoreboard = this.getScoreboard();
        for(ExampleTeam team : this.getArena().getTeamController().getTeams()) {
            for (Player player : team.getBukkitPlayers()) {
                player.setScoreboard(scoreboard);
            }
        }
    }

    @Override
    public void beforeRoundStarting() {

    }

    @Override
    public void onRoundEnd() {

    }


    @Override
    public void onInit() {

    }

    @Override
    public void beforeGameStarting() {

    }

    @Override
    public void gameEnded() {
        for(ExampleTeam team : this.getArena().getTeamController().getTeams()) {
            for (Player player : team.getBukkitPlayers()) {
                player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            }
        }
    }*/
}
