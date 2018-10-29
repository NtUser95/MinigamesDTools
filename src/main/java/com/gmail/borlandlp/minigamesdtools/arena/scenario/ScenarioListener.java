package com.gmail.borlandlp.minigamesdtools.arena.scenario;

import com.gmail.borlandlp.minigamesdtools.arena.ArenaEventListener;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerDeathLocalEvent;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerJoinLocalEvent;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerKilledLocalEvent;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerLeaveLocalEvent;

public class ScenarioListener implements ArenaEventListener {
    private ScenarioController scenarioController;

    public ScenarioListener(ScenarioController scenarioController) {
        this.scenarioController = scenarioController;
    }

    public void onPlayerDeath(ArenaPlayerDeathLocalEvent event) {

    }

    public void onPlayerKilled(ArenaPlayerKilledLocalEvent event) {

    }

    public void onPlayerLeave(ArenaPlayerLeaveLocalEvent event) {

    }

    public void onPlayerJoin(ArenaPlayerJoinLocalEvent event) {

    }
}
