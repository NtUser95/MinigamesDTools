package com.gmail.borlandlp.minigamesdtools.arena.scenario.simplyteampvp;

import com.gmail.borlandlp.minigamesdtools.arena.ArenaEventListener;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaEventHandler;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerDeathLocalEvent;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerKilledLocalEvent;
import com.gmail.borlandlp.minigamesdtools.arena.scenario.Scenario;


public class ExampleScenarioListener implements ArenaEventListener {
    private Scenario scenario;

    public ExampleScenarioListener(Scenario s) {
        this.scenario = s;
    }

    @ArenaEventHandler
    public void playerDied(ArenaPlayerDeathLocalEvent event) {

    }

    @ArenaEventHandler
    public void playerKilled(ArenaPlayerKilledLocalEvent event) {

    }
}
