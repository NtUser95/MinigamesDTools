package com.gmail.borlandlp.minigamesdtools.conditions;

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub;

public class ConditionsCreatorHub extends CreatorHub {
    public AbstractCondition createCondition(String ID, AbstractDataProvider dataProvider) throws Exception {
        return (AbstractCondition) this.create(ID, dataProvider);
    }
}
