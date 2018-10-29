package com.gmail.borlandlp.minigamesdtools.arena.scenario.simplyteampvp;

import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase;
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.Creator;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo;

@CreatorInfo(creatorId = "example_scenario_two")
public class ExampleTwoScenarioCreator implements Creator {
    @Override
    public Object create(String ID, AbstractDataProvider dataProvider) throws Exception {
        ExampleTwoScenario exampleScenario = new ExampleTwoScenario();
        exampleScenario.setArena((ArenaBase) dataProvider.get("arena_instance"));

        return exampleScenario;
    }
}
