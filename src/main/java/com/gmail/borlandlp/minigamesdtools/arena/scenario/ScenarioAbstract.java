package com.gmail.borlandlp.minigamesdtools.arena.scenario;

import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase;

import java.util.ArrayList;
import java.util.List;

public abstract class ScenarioAbstract implements Scenario {
    private ArenaBase arenaBase;
    private List<Scenario> parents = new ArrayList<>();

    public ArenaBase getArena() {
        return arenaBase;
    }

    public void setArena(ArenaBase arenaBase) {
        this.arenaBase = arenaBase;
    }

    public boolean hasActiveParents() {
        for (Scenario scenario : this.getParents()) {
            if(!scenario.isDone()) return true;
        }

        return false;
    }

    public List<Scenario> getParents() {
        return this.parents;
    }

    public void addParent(Scenario scenario) {
        this.parents.add(scenario);
    }

    public void removeParent(Scenario scenario) {
        this.parents.remove(scenario);
    }
}
