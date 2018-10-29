package com.gmail.borlandlp.minigamesdtools.gui.inventory;

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.Creator;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo;

@CreatorInfo(creatorId = "default_view_inventory")
public class DefaultViewInventoryCreator implements Creator {
    @Override
    public DrawableInventory create(String ID, AbstractDataProvider dataProvider) throws Exception {
        return new DefaultViewInventory();
    }
}
