package com.gmail.borlandlp.minigamesdtools.arena;

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub;

public class ArenaCreatorHub extends CreatorHub {
    public ArenaBase createArena(String ID, AbstractDataProvider dataProvider) throws Exception {
        return (ArenaBase) this.create(ID, dataProvider);
    }
}
