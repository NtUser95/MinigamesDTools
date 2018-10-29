package com.gmail.borlandlp.minigamesdtools.gui.inventory;

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub;

public class InventoryGUICreatorHub extends CreatorHub {
    public DrawableInventory createInventory(String ID, AbstractDataProvider dataProvider) throws Exception {
        return (DrawableInventory) this.create(ID, dataProvider);
    }
}
