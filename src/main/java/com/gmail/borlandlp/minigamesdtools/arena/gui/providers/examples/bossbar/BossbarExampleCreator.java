package com.gmail.borlandlp.minigamesdtools.arena.gui.providers.examples.bossbar;

import com.gmail.borlandlp.minigamesdtools.arena.gui.providers.GUIProvider;
import com.gmail.borlandlp.minigamesdtools.arena.gui.providers.examples.bossbar.BossbarExample;
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.Creator;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo;

import java.util.ArrayList;
import java.util.List;

@CreatorInfo(creatorId = "bossbar_example")
public class BossbarExampleCreator implements Creator {
    @Override
    public List<String> getDataProviderRequiredFields() {
        return new ArrayList<>();
    }

    @Override
    public GUIProvider create(String ID, AbstractDataProvider dataProvider) throws Exception {
        return new BossbarExample();
    }
}
