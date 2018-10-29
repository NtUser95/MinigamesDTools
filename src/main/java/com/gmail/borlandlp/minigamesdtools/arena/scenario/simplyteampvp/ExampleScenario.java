package com.gmail.borlandlp.minigamesdtools.arena.scenario.simplyteampvp;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaEventListener;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaEventHandler;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerJoinLocalEvent;
import com.gmail.borlandlp.minigamesdtools.arena.scenario.ScenarioAbstract;

public class ExampleScenario extends ScenarioAbstract {
    private boolean done;
    private ArenaEventListener listener;

    @Override
    public boolean isDone() {
        return this.done;
    }

    @Override
    public void update() {
                /*
        for (ExampleTeam team : this.getArena().getTeamController().getTeams()) {
            for (String nickname : team.getSourcePlayers().keySet()) {
                Player player = team.getSourcePlayers().get(nickname);
                if (player == null || !player.isOnline()) {
                    //timeout
                    if (this.getArena().leaversInFight.containsKey(nickname)) {
                        this.getArena().leaversInFight.put(nickname, this.getArena().leaversInFight.get(nickname) + 1);
                    } else {
                        this.getArena().leaversInFight.put(nickname, 1);
                        this.getArena().broadcastMessage(nickname + " вышел с сервера во время матча, даём ему 30 секунд на возвращение..", false);
                    }

                    if (this.getArena().leaversInFight.containsKey(nickname) && this.getArena().leaversInFight.get(nickname) > 30) {
                        try {
                            this.playerKilled(nickname);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        */
    }

    @Override
    public void onInit() {

    }

    @Override
    public void beforeGameStarting() {
        this.listener = new ExampleScenarioListener(this);
        try {
            this.getArena().getEventAnnouncer().register(this.listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void gameEnded() {
        try {
            MinigamesDTools.getInstance().getActivePointsAPI().deactivatePoint(MinigamesDTools.getInstance().getActivePointsAPI().searchPointByID("example_sphereactivepoint"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            MinigamesDTools.getInstance().getActivePointsAPI().deactivatePoint(MinigamesDTools.getInstance().getActivePointsAPI().searchPointByID("example_squareactivepoint"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            MinigamesDTools.getInstance().getActivePointsAPI().deactivatePoint(MinigamesDTools.getInstance().getActivePointsAPI().searchPointByID("example_livingentity"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.getArena().getEventAnnouncer().unregister(this.listener);
    }

    @Override
    public void beforeRoundStarting() {
        try {
            MinigamesDTools.getInstance().getActivePointsAPI().activatePoint(MinigamesDTools.getInstance().getActivePointsAPI().searchPointByID("example_sphereactivepoint"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            MinigamesDTools.getInstance().getActivePointsAPI().activatePoint(MinigamesDTools.getInstance().getActivePointsAPI().searchPointByID("example_squareactivepoint"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            MinigamesDTools.getInstance().getActivePointsAPI().activatePoint(MinigamesDTools.getInstance().getActivePointsAPI().searchPointByID("example_livingentity"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRoundEnd() {

    }

    @Override
    public boolean roundMustBeEnded() {
        return false;
    }

    @Override
    public boolean gameMustBeEnded() {
        return false;
    }

    @ArenaEventHandler
    public void onEvent(ArenaPlayerJoinLocalEvent event) {
        System.out.println(this.getClass().getSimpleName() + "#" + event);
    }
}
