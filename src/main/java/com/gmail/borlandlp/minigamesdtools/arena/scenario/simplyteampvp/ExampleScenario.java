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

        try {
            MinigamesDTools.getInstance().getActivePointsAPI().activatePoint(MinigamesDTools.getInstance().getActivePointsAPI().searchPointByID("example_sphereactivepoint"));
            MinigamesDTools.getInstance().getActivePointsAPI().activatePoint(MinigamesDTools.getInstance().getActivePointsAPI().searchPointByID("example_squareactivepoint"));
            MinigamesDTools.getInstance().getActivePointsAPI().activatePoint(MinigamesDTools.getInstance().getActivePointsAPI().searchPointByID("example_livingentity"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void gameEnded() {
        try {
            MinigamesDTools.getInstance().getActivePointsAPI().deactivatePoint(MinigamesDTools.getInstance().getActivePointsAPI().searchPointByID("example_sphereactivepoint"));
            MinigamesDTools.getInstance().getActivePointsAPI().deactivatePoint(MinigamesDTools.getInstance().getActivePointsAPI().searchPointByID("example_livingentity"));
            MinigamesDTools.getInstance().getActivePointsAPI().deactivatePoint(MinigamesDTools.getInstance().getActivePointsAPI().searchPointByID("example_squareactivepoint"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.getArena().getEventAnnouncer().unregister(this.listener);
    }

    @Override
    public void beforeRoundStarting() {

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

    }
}
