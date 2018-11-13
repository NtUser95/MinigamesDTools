package com.gmail.borlandlp.minigamesdtools.arena.gui.providers.examples.bossbar;

import com.gmail.borlandlp.minigamesdtools.arena.gui.providers.GUIProvider;
import com.gmail.borlandlp.minigamesdtools.arena.gui.providers.examples.bossbar.BossbarExample;
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.Creator;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo;

@CreatorInfo(creatorId = "bossbar_example")
public class BossbarExampleCreator implements Creator {
    @Override
    public GUIProvider create(String ID, AbstractDataProvider dataProvider) throws Exception {
        return new BossbarExample();
    }
}
