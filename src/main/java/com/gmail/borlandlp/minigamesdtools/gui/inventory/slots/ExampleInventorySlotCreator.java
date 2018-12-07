package com.gmail.borlandlp.minigamesdtools.gui.inventory.slots;

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.Creator;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo;
import com.gmail.borlandlp.minigamesdtools.gui.inventory.slots.ExampleInventorySlot;
import com.gmail.borlandlp.minigamesdtools.gui.inventory.slots.InventorySlot;

import java.util.ArrayList;
import java.util.List;

@CreatorInfo(creatorId = "default_view_inventory_slot")
public class ExampleInventorySlotCreator implements Creator {
    @Override
    public List<String> getDataProviderRequiredFields() {
        return new ArrayList<>();
    }

    @Override
    public InventorySlot create(String ID, AbstractDataProvider dataProvider) throws Exception {
        return new ExampleInventorySlot();
    }
}
