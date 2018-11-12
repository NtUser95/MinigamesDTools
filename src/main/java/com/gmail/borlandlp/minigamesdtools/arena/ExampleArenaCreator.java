package com.gmail.borlandlp.minigamesdtools.arena;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.arena.chunkloader.ChunkLoaderController;
import com.gmail.borlandlp.minigamesdtools.arena.chunkloader.ChunksLoader;
import com.gmail.borlandlp.minigamesdtools.conditions.AbstractCondition;
import com.gmail.borlandlp.minigamesdtools.conditions.ConditionsChain;
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath;
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.Creator;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo;
import com.gmail.borlandlp.minigamesdtools.creator.DataProvider;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.Hotbar;
import com.gmail.borlandlp.minigamesdtools.arena.gui.hotbar.HotbarController;
import com.gmail.borlandlp.minigamesdtools.arena.gui.providers.GUIController;
import com.gmail.borlandlp.minigamesdtools.arena.gui.providers.GUIProvider;
import com.gmail.borlandlp.minigamesdtools.arena.scenario.ScenarioChainController;
import com.gmail.borlandlp.minigamesdtools.arena.team.TeamController;
import com.gmail.borlandlp.minigamesdtools.arena.team.TeamProvider;
import com.gmail.borlandlp.minigamesdtools.util.ArenaUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CreatorInfo(creatorId = "default_arena")
public class ExampleArenaCreator implements Creator {
    @Override
    public ArenaBase create(String ID, AbstractDataProvider dataProvider) throws Exception {
        ArenaBase arenaTemplate = new ArenaBase(ID);
        String debugPrefix = "[" + ID + "] ";
        Debug.print(Debug.LEVEL.NOTICE, debugPrefix + " started loading...");

        Debug.print(Debug.LEVEL.NOTICE, debugPrefix + " load options...");
        ConfigurationSection arenaConfig = MinigamesDTools.getInstance().getConfigProvider().getEntity(ConfigPath.ARENA_FOLDER, ID).getData();
        arenaTemplate.setEnabled(arenaConfig.get("enabled").toString().equalsIgnoreCase("true"));
        arenaTemplate.setRoundTime(Integer.parseInt(arenaConfig.get("roundTime").toString()));
        arenaTemplate.setMaxRounds(Integer.parseInt(arenaConfig.get("maxRounds").toString()));
        arenaTemplate.setArenaCost(Integer.parseInt(arenaConfig.get("eco.arenaCost").toString()));
        arenaTemplate.setCanItemDrop(Boolean.parseBoolean(arenaConfig.get("playerItemDrop").toString()));
        arenaTemplate.setCanItemPickup(Boolean.parseBoolean(arenaConfig.get("playerItemPickUp").toString()));
        arenaTemplate.setCountdown_time(Integer.parseInt(arenaConfig.get("countdown.beforeFight.duration").toString()));
        arenaTemplate.setPreCountdown_time(Integer.parseInt(arenaConfig.get("countdown.beforeTeleport.duration").toString()));
        arenaTemplate.setCountdown_disableMoving(Boolean.parseBoolean(arenaConfig.get("countdown.beforeFight.disableMoving").toString()));
        arenaTemplate.setHungerDisable(Boolean.parseBoolean(arenaConfig.get("playerHungerDisable").toString()));
        arenaTemplate.setMinPlayersToStart(Integer.parseInt(arenaConfig.get("min_players_to_start").toString()));
        if(arenaConfig.contains("regain_health")) {
            arenaTemplate.setRegainHealth(arenaConfig.get("regain_health").toString().equalsIgnoreCase("true"));
        }

        Debug.print(Debug.LEVEL.NOTICE, debugPrefix + " load chunkLoaders...");
        ChunkLoaderController chunkLoaderController = new ChunkLoaderController();
        chunkLoaderController.setArena(arenaTemplate);
        Map<String, ChunksLoader> chunksLoaderMap = new HashMap<>();
        if(arenaConfig.contains("chunk_loaders")) {
            for (String loaderName : arenaConfig.getConfigurationSection("chunk_loaders").getKeys(false)) {
                if(arenaConfig.contains("chunk_loaders." + loaderName + ".min_point_xz") && arenaConfig.contains("chunk_loaders." + loaderName + ".max_point_xz")) {
                    String[] minXZ = arenaConfig.get("chunk_loaders." + loaderName + ".min_point_xz").toString().split(":");
                    String[] maxXZ = arenaConfig.get("chunk_loaders." + loaderName + ".max_point_xz").toString().split(":");
                    World world = Bukkit.getWorld(arenaConfig.get("chunk_loaders." + loaderName + ".world").toString());
                    Location min_loc = world.getBlockAt(Integer.parseInt(minXZ[0]), 1, Integer.parseInt(minXZ[1])).getLocation();
                    Location max_loc = world.getBlockAt(Integer.parseInt(maxXZ[0]), 1, Integer.parseInt(maxXZ[1])).getLocation();

                    chunksLoaderMap.put(loaderName, MinigamesDTools.getInstance().getChunkLoaderCreator().buildChunkLoader(world, min_loc, max_loc));
                } else {
                    Debug.print(Debug.LEVEL.NOTICE, "ChunkLoaderConfig does not contain a field 'min_point_xz' or 'max_point_xz' -> Skipping it.");
                }
            }

            chunkLoaderController.setLoaders(chunksLoaderMap);
        } else {
            Debug.print(Debug.LEVEL.NOTICE, "ArenaConfig does not contain a field 'ChunkLoaders' -> Skipping it.");
        }
        arenaTemplate.setChunkLoaderController(chunkLoaderController);

        Debug.print(Debug.LEVEL.NOTICE, debugPrefix + " load GUIController...");
        GUIController guiController = new GUIController();
        guiController.setArena(arenaTemplate);
        for (String GUI_ID : arenaConfig.getStringList("gui_provider")) {
            GUIProvider guiProvider = MinigamesDTools.getInstance().getGuiCreatorHub().createGuiProvider(GUI_ID, new DataProvider());
            guiProvider.setArena(arenaTemplate);
            guiController.addProvider(guiProvider);
        }
        arenaTemplate.setGuiController(guiController);

        Debug.print(Debug.LEVEL.NOTICE, debugPrefix + " load ScenarioChain...");
        AbstractDataProvider abstractDataProvider = new DataProvider();
        abstractDataProvider.set("arena_instance", arenaTemplate);
        ScenarioChainController scenarioChainController = MinigamesDTools.getInstance().getScenarioChainCreatorHub().createChain(arenaConfig.get("scenarios_chain").toString(), abstractDataProvider);
        arenaTemplate.setScenarioChainController(scenarioChainController);

        Debug.print(Debug.LEVEL.NOTICE, debugPrefix + " load teams...");
        TeamController teamController = new TeamController(arenaTemplate);
        for(String teamID : arenaConfig.getStringList("teams")) {
            TeamProvider teamProvider = MinigamesDTools.getInstance().getTeamCreatorHub().createTeam(teamID, new DataProvider());
            teamProvider.setArena(arenaTemplate);
            teamController.addTeam(teamProvider);
        }
        arenaTemplate.setTeamController(teamController);

        //conditions
        Debug.print(Debug.LEVEL.NOTICE, debugPrefix + " load conditions...");
        List<AbstractCondition> conditions = new ArrayList<>();
        for (String conditionId : arenaConfig.getStringList("join_conditions")) {
            conditions.add(MinigamesDTools.getInstance().getConditionsCreatorHub().createCondition(conditionId, new DataProvider()));
        }
        arenaTemplate.setJoinConditionsChain(new ConditionsChain(conditions));

        Debug.print(Debug.LEVEL.NOTICE, debugPrefix + " load hotbar...");
        HotbarController hotbarController = new HotbarController();
        hotbarController.setArena(arenaTemplate);
        if(Boolean.parseBoolean(arenaConfig.get("interactive_hotbar.enabled").toString())) {
            String hotbarID = arenaConfig.get("interactive_hotbar.hotbar_id").toString();
            if(MinigamesDTools.getInstance().getHotbarCreatorHub().containsRouteId2Creator(hotbarID)) {
                Hotbar hotbar = MinigamesDTools.getInstance().getHotbarCreatorHub().createHotbar(hotbarID, new DataProvider());
                if(hotbar == null) {
                    throw new Exception("Internal error while building Hotbar[ID:" + hotbarID + "]");
                } else {
                    hotbarController.setDefaultHotbar(hotbar);
                    Debug.print(Debug.LEVEL.NOTICE, "Hotbar is enabled for Arena " + ID);
                    hotbarController.setEnabled(true);
                }
            } else {
                throw new Exception("cant find creator for hotbar[ID:" + hotbarID + "]");
            }
        } else {
            Debug.print(Debug.LEVEL.NOTICE, "Hotbar is disabled for Arena " + ID);
        }
        arenaTemplate.setHotbarController(hotbarController);

        Debug.print(Debug.LEVEL.NOTICE, debugPrefix + "generate random ID...");
        arenaTemplate.setGameId(ArenaUtils.generateRandID().substring(0, 14));

        Debug.print(Debug.LEVEL.NOTICE, debugPrefix + "register phase components...");
        // register
        arenaTemplate.getPhaseComponentController().register(arenaTemplate.getChunkLoaderController());
        arenaTemplate.getPhaseComponentController().register(arenaTemplate.getTeamController());
        arenaTemplate.getPhaseComponentController().register(arenaTemplate.getScenarioChainController());
        arenaTemplate.getPhaseComponentController().register(arenaTemplate.getGuiController());
        arenaTemplate.getPhaseComponentController().register(arenaTemplate.getHotbarController());
        arenaTemplate.getPhaseComponentController().register(arenaTemplate.getHandlersController());
        Debug.print(Debug.LEVEL.NOTICE, debugPrefix + "loading is done!");

        return arenaTemplate;
    }
}
