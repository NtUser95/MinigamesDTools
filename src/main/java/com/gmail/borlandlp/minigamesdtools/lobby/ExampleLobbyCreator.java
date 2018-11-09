package com.gmail.borlandlp.minigamesdtools.lobby;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath;
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.Creator;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo;
import com.gmail.borlandlp.minigamesdtools.util.ArenaUtils;
import org.bukkit.configuration.ConfigurationSection;

@CreatorInfo(creatorId = "default_server_lobby")
public class ExampleLobbyCreator implements Creator {
    @Override
    public Object create(String ID, AbstractDataProvider dataProvider) throws Exception {
        ConfigurationSection conf = MinigamesDTools.getInstance().getConfigProvider().getEntity(ConfigPath.SERVER_LOBBY, ID).getData();
        if(conf == null) {
            throw new Exception("Cant find config for " + ID);
        }

        ServerLobby serverLobby = new ExampleServerLobby();

        serverLobby.setHotbarID(conf.contains("hotbar_id") ? conf.get("hotbar_id").toString() : null);
        serverLobby.setID(ID);
        serverLobby.setSpawnPoint(ArenaUtils.str2Loc(conf.get("spawn_point_XYZWorldYawPitch").toString().split(":")));

        return serverLobby;
    }
}
