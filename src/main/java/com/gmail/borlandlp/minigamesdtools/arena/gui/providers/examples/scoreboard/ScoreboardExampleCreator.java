package com.gmail.borlandlp.minigamesdtools.arena.gui.providers.examples.scoreboard;

import com.gmail.borlandlp.minigamesdtools.arena.gui.providers.GUIProvider;
import com.gmail.borlandlp.minigamesdtools.arena.gui.providers.examples.scoreboard.ScoreboardExample;
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.Creator;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo;

import java.util.ArrayList;
import java.util.List;

@CreatorInfo(creatorId = "scoreboard_example")
public class ScoreboardExampleCreator implements Creator {
    @Override
    public List<String> getDataProviderRequiredFields() {
        return new ArrayList<>();
    }

    @Override
    public GUIProvider create(String ID, AbstractDataProvider dataProvider) throws Exception {
        return new ScoreboardExample();
    }
}
