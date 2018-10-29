package com.gmail.borlandlp.minigamesdtools.arena.team.lobby.starter;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.ArenaLobby;
import com.gmail.borlandlp.minigamesdtools.config.ConfigManager;
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.Creator;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo;
import com.gmail.borlandlp.minigamesdtools.creator.DataProvider;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.Hotbar;
import com.gmail.borlandlp.minigamesdtools.util.ArenaUtils;
import org.bukkit.configuration.ConfigurationSection;

@CreatorInfo(creatorId = "default_starter_lobby")
public class StarterLobbyCreator implements Creator {
    @Override
    public ArenaLobby create(String ID, AbstractDataProvider dataProvider) throws Exception {
        ExampleStarterLobby lobby = new ExampleStarterLobby();

        ConfigurationSection conf = MinigamesDTools.getInstance().getConfigManager().getConfigSection(ConfigManager.ConfigPath.STARTER_LOBBY, ID);
        Hotbar hotbar = null;
        try {
            hotbar = MinigamesDTools.getInstance().getHotbarCreatorHub().createHotbar(conf.get("hotbar").toString(), new DataProvider());
        } catch (Exception e) {
            e.printStackTrace();
        }
        lobby.setDefaultHotbar(hotbar);

        String[] splittedSpawnPoint = conf.get("location_XYZWorldYawPitch").toString().split(":");
        lobby.setSpawnPoint(ArenaUtils.str2Loc(splittedSpawnPoint));

        return lobby;
    }
}
