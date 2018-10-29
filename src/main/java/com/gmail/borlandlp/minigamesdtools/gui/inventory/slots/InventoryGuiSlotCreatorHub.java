package com.gmail.borlandlp.minigamesdtools.gui.inventory.slots;

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub;

public class InventoryGuiSlotCreatorHub extends CreatorHub {
    public InventorySlot createSlot(String ID, AbstractDataProvider dataProvider) throws Exception {
        return (InventorySlot) this.create(ID, dataProvider);
    }
}
