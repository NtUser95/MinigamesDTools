package com.gmail.borlandlp.minigamesdtools.gui.hotbar;

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub;

public class HotbarCreatorHub extends CreatorHub {
    public Hotbar createHotbar(String ID, AbstractDataProvider dataProvider) throws Exception {
        return (Hotbar) this.create(ID, dataProvider);
    }
}
