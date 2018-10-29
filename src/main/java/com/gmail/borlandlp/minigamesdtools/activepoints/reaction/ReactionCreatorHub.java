package com.gmail.borlandlp.minigamesdtools.activepoints.reaction;

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub;

public class ReactionCreatorHub extends CreatorHub {
    public Reaction createReaction(String ID, AbstractDataProvider dataProvider) throws Exception {
        return (Reaction) this.create(ID, dataProvider);
    }
}
