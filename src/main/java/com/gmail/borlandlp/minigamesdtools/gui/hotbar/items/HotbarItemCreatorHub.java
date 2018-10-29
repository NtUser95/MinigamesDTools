package com.gmail.borlandlp.minigamesdtools.gui.hotbar.items;

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub;

public class HotbarItemCreatorHub extends CreatorHub {
    public SlotItem createHotbarItem(String ID, AbstractDataProvider dataProvider) throws Exception {
        return (SlotItem) this.create(ID, dataProvider);
    }
}
