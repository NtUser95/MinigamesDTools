package com.gmail.borlandlp.minigamesdtools.arena.gui.providers.examples;

import com.gmail.borlandlp.minigamesdtools.arena.gui.providers.GUIProvider;
import com.gmail.borlandlp.minigamesdtools.arena.gui.providers.IGUIFactory;
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.Creator;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo;

@CreatorInfo(creatorId = "scoreboard_example")
public class ScoreboardExampleCreator implements Creator {
    @Override
    public GUIProvider create(String ID, AbstractDataProvider dataProvider) throws Exception {
        return new ScoreboardExample();
    }
}
