package com.gmail.borlandlp.minigamesdtools.arena.scenario;

import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent;

import java.util.ArrayList;
import java.util.List;

public class ScenarioController implements ArenaPhaseComponent {
    private List<Scenario> scenarios = new ArrayList<>();
    private List<Scenario> activeScenarios = new ArrayList<>();
    private ScenarioListener scenarioListener;
    private Scenario endGameInitiator;
    private boolean signalRoundEnding;
    private Scenario roundEndInitiator;
    private boolean signalGameEnding;
    private ArenaBase arena;

    public ScenarioController(ArenaBase arena) {
        this.arena = arena;
    }

    public ArenaBase getArena() {
        return arena;
    }

    public boolean hasSignalGameEnding() {
        return signalGameEnding;
    }

    public boolean hasSignalRoundEnding() {
        return signalRoundEnding;
    }

    public Scenario getEndGameInitiator() {
        return endGameInitiator;
    }

    public Scenario getEndRoundInitiator() {
        return roundEndInitiator;
    }

    public List<Scenario> getScenarios() {
        return scenarios;
    }

    public List<Scenario> getActiveScenarios() {
        return activeScenarios;
    }

    private void updateScenariosQueue() {
        for(Scenario scenario : this.getScenarios()) {
            if(!((ScenarioAbstract) scenario).hasActiveParents() && !scenario.isDone() && !this.getActiveScenarios().contains(scenario)) {
                this.enableScenario(scenario);
            }
        }
    }

    @Override
    public void onInit() {
        this.scenarioListener = new ScenarioListener(this);
        try {
            this.getArena().getEventAnnouncer().register(this.scenarioListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.updateScenariosQueue();
    }

    public void beforeGameStarting() {
        this.updateScenariosQueue();
    }

    public void gameEnded() {

    }

    public void setScenarios(List<Scenario> scenarios) {
        this.scenarios = scenarios;
    }

    public void resetSignalsData() {
        this.signalGameEnding = false;
        this.endGameInitiator = null;
        this.signalRoundEnding = false;
        this.roundEndInitiator = null;
    }

    private void enableScenario(Scenario scenario) {
        this.activeScenarios.add(scenario);
        this.getArena().getPhaseComponentController().register(scenario);
    }

    private void disableScenario(Scenario scenario) {
        this.activeScenarios.remove(scenario);
        this.getArena().getPhaseComponentController().unregister(scenario);
    }

    public void update() {
        List<Scenario> scenarioIterator = new ArrayList<>(this.activeScenarios);
        for (Scenario scenario : scenarioIterator) {
            if(scenario.isDone()) {
                this.disableScenario(scenario);
                this.updateScenariosQueue();
            } else {
                // scenario.updateScenario();

                if(scenario.gameMustBeEnded()) {
                    this.signalGameEnding = true;
                    this.endGameInitiator = scenario;
                    return;
                } else if(scenario.roundMustBeEnded()) {
                    this.signalRoundEnding = true;
                    this.roundEndInitiator = scenario;
                    return;
                }
            }
        }
    }

    @Override
    public void beforeRoundStarting() {

    }

    @Override
    public void onRoundEnd() {
        this.resetSignalsData();
    }
}
