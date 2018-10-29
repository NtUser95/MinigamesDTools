package com.gmail.borlandlp.minigamesdtools.arena.gui.providers;

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub;

public class GUICreatorHub extends CreatorHub {
    public GUIProvider createGuiProvider(String ID, AbstractDataProvider dataProvider) throws Exception {
        return (GUIProvider) this.create(ID, dataProvider);
    }
}
