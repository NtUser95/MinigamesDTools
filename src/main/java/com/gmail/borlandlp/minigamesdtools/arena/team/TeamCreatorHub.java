package com.gmail.borlandlp.minigamesdtools.arena.team;

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub;

public class TeamCreatorHub extends CreatorHub {
    public TeamProvider createTeam(String ID, AbstractDataProvider dataProvider) throws Exception {
        return (TeamProvider) this.create(ID, dataProvider);
    }
}
