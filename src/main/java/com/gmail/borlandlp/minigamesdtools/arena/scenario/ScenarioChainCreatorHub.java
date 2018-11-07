package com.gmail.borlandlp.minigamesdtools.arena.scenario;

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub;

public class ScenarioChainCreatorHub extends CreatorHub {
    public ScenarioChainController createChain(String ID, AbstractDataProvider dataProvider) throws Exception {
        return (ScenarioChainController) this.create(ID, dataProvider);
    }
}
