package com.gmail.borlandlp.minigamesdtools.activepoints.reaction.custom;

import com.gmail.borlandlp.minigamesdtools.activepoints.ActivePoint;
import com.gmail.borlandlp.minigamesdtools.activepoints.reaction.Reaction;
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.Creator;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo;

import java.util.ArrayList;
import java.util.List;

@CreatorInfo(creatorId = "item_give_reaction")
public class ItemGiveReactionCreator implements Creator {
    @Override
    public List<String> getDataProviderRequiredFields() {
        return new ArrayList<>();
    }

    @Override
    public Object create(String ID, AbstractDataProvider dataProvider) throws Exception {
        Reaction reaction = new ItemGiveReaction();
        reaction.setActivePoint((ActivePoint) dataProvider.get("active_point_instance"));

        return reaction;
    }
}
