package com.gmail.borlandlp.minigamesdtools.conditions.examples;

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.Creator;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo;

@CreatorInfo(creatorId = "empty_inventory")
public class EmptyInventoryConditionCreator implements Creator {
    @Override
    public Object create(String ID, AbstractDataProvider dataProvider) throws Exception {
        return new EmptyInventoryCondition();
    }
}
