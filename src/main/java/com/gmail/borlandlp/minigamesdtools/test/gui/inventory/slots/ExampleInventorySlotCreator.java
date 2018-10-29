package com.gmail.borlandlp.minigamesdtools.test.gui.inventory.slots;

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.Creator;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo;
import com.gmail.borlandlp.minigamesdtools.gui.inventory.slots.InventorySlot;

@CreatorInfo(creatorId = "default_view_inventory_slot")
public class ExampleInventorySlotCreator implements Creator {
    @Override
    public InventorySlot create(String ID, AbstractDataProvider dataProvider) throws Exception {
        return new ExampleInventorySlot();
    }
}
