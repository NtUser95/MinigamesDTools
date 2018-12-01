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
import com.gmail.borlandlp.minigamesdtools.arena.gui.hotbar.HotbarController;
import com.gmail.borlandlp.minigamesdtools.arena.gui.providers.GUIController;
import com.gmail.borlandlp.minigamesdtools.arena.gui.providers.GUIProvider;
import com.gmail.borlandlp.minigamesdtools.arena.scenario.ScenarioChainController;
import com.gmail.borlandlp.minigamesdtools.arena.team.TeamController;
import com.gmail.borlandlp.minigamesdtools.arena.team.TeamProvider;
import com.gmail.borlandlp.minigamesdtools.util.ArenaUtils;
import com.gmail.borlandlp.minigamesdtools.util.Glowing;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

@CreatorInfo(creatorId = "default_arena")
public class ExampleArenaCreator implements Creator {
    @Override
    public List<String> getDataProviderRequiredFields() {
        return new ArrayList<>();
    }

    @Override
    public ArenaBase create(String ID, AbstractDataProvider dataProvider) throws Exception {
        //ArenaBase arenaTemplate = new ArenaBase();
        String debugPrefix = "[" + ID + "] ";
        ConfigurationSection arenaConfig = MinigamesDTools.getInstance().getConfigProvider().getEntity(ConfigPath.ARENA_FOLDER, ID).getData();
        Debug.print(Debug.LEVEL.NOTICE, debugPrefix + " started loading...");

        ArenaBase.Builder builder = ArenaBase.newBuilder();

        Debug.print(Debug.LEVEL.NOTICE, debugPrefix + " load chunkLoaders...");
        ChunkLoaderController chunkLoaderController = new ChunkLoaderController();
        chunkLoaderController.setArena(builder.getArena());
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
        builder.setChunkLoaderController(chunkLoaderController);

        Debug.print(Debug.LEVEL.NOTICE, debugPrefix + " load GUIController...");
        GUIController guiController = new GUIController();
        guiController.setArena(builder.getArena());
        for (String GUI_ID : arenaConfig.getStringList("gui_provider")) {
            GUIProvider guiProvider = MinigamesDTools.getInstance().getGuiCreatorHub().createGuiProvider(GUI_ID, new DataProvider());
            guiProvider.setArena(builder.getArena());
            guiController.addProvider(guiProvider);
        }
        builder.setGuiController(guiController);

        Debug.print(Debug.LEVEL.NOTICE, debugPrefix + " load ScenarioChain...");
        AbstractDataProvider abstractDataProvider = new DataProvider();
        abstractDataProvider.set("arena_instance", builder.getArena());
        ScenarioChainController scenarioChainController = MinigamesDTools.getInstance().getScenarioChainCreatorHub().createChain(arenaConfig.get("scenarios_chain").toString(), abstractDataProvider);
        builder.setScenarioChainController(scenarioChainController);

        Debug.print(Debug.LEVEL.NOTICE, debugPrefix + " load teams...");
        TeamController teamController = new TeamController(builder.getArena());
        for(String teamID : arenaConfig.getStringList("teams")) {
            TeamProvider teamProvider = MinigamesDTools.getInstance().getTeamCreatorHub().createTeam(teamID, new DataProvider());
            teamProvider.setArena(builder.getArena());
            teamController.addTeam(teamProvider);
        }
        builder.setTeamController(teamController);

        //conditions
        Debug.print(Debug.LEVEL.NOTICE, debugPrefix + " load conditions...");
        List<AbstractCondition> conditions = new ArrayList<>();
        for (String conditionId : arenaConfig.getStringList("join_conditions")) {
            conditions.add(MinigamesDTools.getInstance().getConditionsCreatorHub().createCondition(conditionId, new DataProvider()));
        }
        builder.setJoinConditionsChain(new ConditionsChain(conditions));

        Debug.print(Debug.LEVEL.NOTICE, debugPrefix + " load hotbar...");
        HotbarController hotbarController = new HotbarController();
        hotbarController.setArena(builder.getArena());
        if(Boolean.parseBoolean(arenaConfig.get("interactive_hotbar.enabled").toString())) {
            String hotbarID = arenaConfig.get("interactive_hotbar.hotbar_id").toString();
            hotbarController.setDefaultHotbarId(hotbarID);
            hotbarController.setEnabled(true);
        } else {
            Debug.print(Debug.LEVEL.NOTICE, "Hotbar is disabled for Arena " + ID);
        }
        builder.setHotbarController(hotbarController);

        Debug.print(Debug.LEVEL.NOTICE, debugPrefix + "generate random ID...");
        builder.setGameId(ArenaUtils.generateRandID().substring(0, 14));

        builder.setName(ID);

        // gen colors
        /*
         * GlowAPI contains the last element color "None". We exclude it.
         * */
        if( builder.getArena().getTeamController().getTeams().size() > Glowing.getAvailableColors().size()-1) {
            throw new Exception("Max team limit reached! [limit=" + (Glowing.getAvailableColors().size()-1) + "]");
        }
        List<String> usedColors = new ArrayList<>();
        for (TeamProvider team : builder.getArena().getTeamController().getTeams()) {
            ChatColor color;
            String strColor;
            do {
                int rnd = new Random().nextInt(Glowing.getAvailableColors().size() - 2); // exclude last color GlowAPI.Color -> "None"
                strColor = Glowing.getAvailableColors().get(rnd);
            } while(usedColors.contains(strColor));
            usedColors.add(strColor);
            team.setColor(strColor);
        }

        // <GameRules>
        Debug.print(Debug.LEVEL.NOTICE, debugPrefix + " load options...");
        GameRules gameRules = new GameRules();
        gameRules.roundTime = Integer.parseInt(arenaConfig.get("roundTime").toString());
        gameRules.maxRounds = Integer.parseInt(arenaConfig.get("maxRounds").toString());
        gameRules.playerCanItemDrop = Boolean.parseBoolean(arenaConfig.get("playerItemDrop").toString());
        gameRules.playerCanItemPickup = Boolean.parseBoolean(arenaConfig.get("playerItemPickUp").toString());
        gameRules.beforeRoundStartingWaitDuration = Integer.parseInt(arenaConfig.get("countdown.beforeFight.duration").toString());
        gameRules.beforeArenaTeleportWaitDuration = Integer.parseInt(arenaConfig.get("countdown.beforeTeleport.duration").toString());
        gameRules.beforeFightDisableMoving = Boolean.parseBoolean(arenaConfig.get("countdown.beforeFight.disableMoving").toString());
        gameRules.hungerDisable = Boolean.parseBoolean(arenaConfig.get("playerHungerDisable").toString());
        gameRules.minPlayersToStart = Integer.parseInt(arenaConfig.get("min_players_to_start").toString());
        if(arenaConfig.contains("regain_health")) {
            gameRules.playerCanRegainHealth = arenaConfig.get("regain_health").toString().equalsIgnoreCase("true");
        }
        builder.setGameRules(gameRules);
        // </GameRules>

        ArenaBase arenaTemplate = builder.build();
        arenaTemplate.setEnabled(arenaConfig.get("enabled").toString().equalsIgnoreCase("true"));

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
