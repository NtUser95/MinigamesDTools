package com.gmail.borlandlp.minigamesdtools.arena.scenario;

import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent;

public interface Scenario extends ArenaPhaseComponent {
    boolean isDone();

    boolean roundMustBeEnded();
    boolean gameMustBeEnded();
}
