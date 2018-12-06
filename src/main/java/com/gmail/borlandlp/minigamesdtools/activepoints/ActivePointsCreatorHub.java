package com.gmail.borlandlp.minigamesdtools.activepoints;

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub;

public class ActivePointsCreatorHub extends CreatorHub {
    public ActivePoint createActivePoint(String ID, AbstractDataProvider dataProvider) throws Exception {
        return (ActivePoint) this.create(ID, dataProvider);
    }
}
