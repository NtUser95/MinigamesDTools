package com.gmail.borlandlp.minigamesdtools.arena.team.lobby.respawn;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.ArenaLobby;
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath;
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.Creator;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo;
import com.gmail.borlandlp.minigamesdtools.creator.DataProvider;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.Hotbar;
import com.gmail.borlandlp.minigamesdtools.util.ArenaUtils;
import org.bukkit.configuration.ConfigurationSection;

@CreatorInfo(creatorId = "default_respawn_lobby")
public class ExampleRespawnLobbyCreator implements Creator {
    @Override
    public ArenaLobby create(String ID, AbstractDataProvider dataProvider) throws Exception {
        ExampleRespawnLobby lobby = new ExampleRespawnLobby();

        ConfigurationSection conf = MinigamesDTools.getInstance().getConfigProvider().getEntity(ConfigPath.ARENA_LOBBY, ID).getData();
        if(conf.contains("hotbar.enabled") && conf.get("hotbar.enabled").toString().equals("true")) {
            Hotbar hotbar = null;
            try {
                hotbar = MinigamesDTools.getInstance().getHotbarCreatorHub().createHotbar(conf.get("hotbar.id").toString(), new DataProvider());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(hotbar == null) {
                throw new Exception("Error while build hotbar for lobby:" + ID);
            }
            lobby.setHotbarEnabled(true);
            lobby.setHotbar(hotbar);
        }

        lobby.setSpawnPoint(ArenaUtils.str2Loc(conf.get("location_XYZWorld").toString().split(":")));
        lobby.setSecondsWaiting(Integer.parseInt(conf.get("seconds_wait").toString()));

        return lobby;
    }
}
