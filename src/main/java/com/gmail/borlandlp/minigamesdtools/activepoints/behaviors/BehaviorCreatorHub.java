package com.gmail.borlandlp.minigamesdtools.activepoints.behaviors;

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub;

public class BehaviorCreatorHub extends CreatorHub {
    public Behavior createBehavior(String ID, AbstractDataProvider dataProvider) throws Exception {
        return (Behavior) this.create(ID, dataProvider);
    }
}
