package com.gmail.borlandlp.minigamesdtools.arena.phasecomponent;

import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent;

import java.util.HashSet;
import java.util.Set;

public class PhaseComponentController {
    private Set<ArenaPhaseComponent> listeners = new HashSet<>();
    private ArenaPhase currentPhase;
    private boolean nowProduceAnnounce = false;

    public void register(ArenaPhaseComponent c) {
        if(this.nowProduceAnnounce) { // for recursive loading listeners at init phase.
            this.announceComponent(c, this.currentPhase);
        }
        this.listeners.add(c);
    }

    private Set<ArenaPhaseComponent> getListeners() {
        return new HashSet<>(this.listeners);
    }

    public void unregister(ArenaPhaseComponent c) {
        this.listeners.remove(c);
    }

    private void announceComponent(ArenaPhaseComponent component, ArenaPhase phase) {
        try {
            switch (phase) {
                case INIT: component.onInit(); break;
                case GAME_STARTING: component.beforeGameStarting(); break;
                case ROUND_STARTING: component.beforeRoundStarting(); break;
                case UPDATE: component.update(); break;
                case ROUND_ENDING: component.onRoundEnd(); break;
                case GAME_ENDING: component.gameEnded(); break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void announceNewPhase(ArenaPhase arenaPhase) {
        this.nowProduceAnnounce = true;
        this.currentPhase = arenaPhase;
        for (ArenaPhaseComponent component : this.getListeners()) {
            this.announceComponent(component, this.currentPhase);
        }
        this.nowProduceAnnounce = false;
    }

    public enum ArenaPhase {
        INIT,
        GAME_STARTING,
        ROUND_STARTING,
        UPDATE,
        ROUND_ENDING,
        GAME_ENDING
    }
}
