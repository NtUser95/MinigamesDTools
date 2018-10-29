package com.gmail.borlandlp.minigamesdtools.arena.team.lobby.respawn;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.ArenaLobby;
import com.gmail.borlandlp.minigamesdtools.config.ConfigManager;
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.Creator;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo;
import com.gmail.borlandlp.minigamesdtools.util.ArenaUtils;
import org.bukkit.configuration.ConfigurationSection;

@CreatorInfo(creatorId = "default_respawn_lobby")
public class ExampleRespawnLobbyCreator implements Creator {
    @Override
    public ArenaLobby create(String ID, AbstractDataProvider dataProvider) throws Exception {
        ExampleRespawnLobby lobby = new ExampleRespawnLobby();

        ConfigurationSection conf = MinigamesDTools.getInstance().getConfigManager().getConfigSection(ConfigManager.ConfigPath.RESPAWN_LOBBY, ID);
        lobby.setSpawnPoint(ArenaUtils.str2Loc(conf.get("location_XYZWorld").toString().split(":")));
        lobby.setSecondsWaiting(Integer.parseInt(conf.get("seconds_wait").toString()));

        return lobby;
    }
}
