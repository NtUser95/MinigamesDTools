package com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.gun;

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.Creator;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.SlotItem;

import java.util.ArrayList;
import java.util.List;

@CreatorInfo(creatorId = "simply_gun_slot_item")
public class SimplyGunCreator implements Creator {
    @Override
    public List<String> getDataProviderRequiredFields() {
        return new ArrayList<>();
    }

    @Override
    public SlotItem create(String ID, AbstractDataProvider dataProvider) throws Exception {
        return null;
    }
}
