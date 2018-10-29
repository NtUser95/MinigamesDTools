package com.gmail.borlandlp.minigamesdtools.lobby;

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub;

public class LobbyCreatorHub extends CreatorHub {
    public ServerLobby createLobby(String ID, AbstractDataProvider dataProvider) throws Exception {
        return (ServerLobby) this.create(ID, dataProvider);
    }
}
