package com.gmail.borlandlp.minigamesdtools.arena.team.lobby;

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub;

public class ArenaLobbyCreatorHub extends CreatorHub {
    public ArenaLobby createLobby(String id, AbstractDataProvider dataProvider) throws Exception {
        return (ArenaLobby) this.create(id, dataProvider);
    }
}
