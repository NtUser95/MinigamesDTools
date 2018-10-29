package com.gmail.borlandlp.minigamesdtools.arena;

public interface ArenaPhaseComponent {
    void onInit();
    void beforeGameStarting();
    void gameEnded();
    void update();
    void beforeRoundStarting();
    void onRoundEnd();
}
