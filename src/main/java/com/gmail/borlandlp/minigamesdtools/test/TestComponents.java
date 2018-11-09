package com.gmail.borlandlp.minigamesdtools.test;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.activepoints.ExampleActivePointsCreator;
import com.gmail.borlandlp.minigamesdtools.activepoints.behaviors.custom.ExampleBehaviorCreator;
import com.gmail.borlandlp.minigamesdtools.activepoints.reaction.custom.ItemGiveReactionCreator;
import com.gmail.borlandlp.minigamesdtools.activepoints.reaction.custom.TeamIncrementWinTicketsReactionCreator;
import com.gmail.borlandlp.minigamesdtools.arena.ExampleArenaCreator;
import com.gmail.borlandlp.minigamesdtools.arena.gui.providers.examples.BossbarExampleCreator;
import com.gmail.borlandlp.minigamesdtools.arena.gui.providers.examples.ScoreboardExampleCreator;
import com.gmail.borlandlp.minigamesdtools.arena.scenario.DefaultScenarioChainCreator;
import com.gmail.borlandlp.minigamesdtools.arena.scenario.simplyteampvp.ExampleScenarioCreator;
import com.gmail.borlandlp.minigamesdtools.arena.scenario.simplyteampvp.ExampleTwoScenarioCreator;
import com.gmail.borlandlp.minigamesdtools.arena.team.ExampleTeamCreator;
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.respawn.ExampleRespawnLobbyCreator;
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.spectator.ExampleSpectatorLobbyCreator;
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.starter.StarterLobbyCreator;
import com.gmail.borlandlp.minigamesdtools.conditions.examples.EmptyInventoryConditionCreator;
import com.gmail.borlandlp.minigamesdtools.conditions.examples.ExampleConditionCreator;
import com.gmail.borlandlp.minigamesdtools.config.ConfigEntity;
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.type.HeldHotbarCreator;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.type.ItemInterractHotbarCreator;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.ExampleItemCreator;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.ShowTeamSpectatorItemCreator;
import com.gmail.borlandlp.minigamesdtools.gui.inventory.DefaultViewInventoryCreator;
import com.gmail.borlandlp.minigamesdtools.test.gui.inventory.slots.ExampleInventorySlotCreator;
import com.gmail.borlandlp.minigamesdtools.lobby.ExampleLobbyCreator;
import com.gmail.borlandlp.minigamesdtools.nmsentities.entity.SilentShulker;
import com.gmail.borlandlp.minigamesdtools.nmsentities.entity.SkyDragon;
import com.gmail.borlandlp.minigamesdtools.nmsentities.entity.SkyZombie;
import net.minecraft.server.v1_12_R1.EntityEnderDragon;
import net.minecraft.server.v1_12_R1.EntityShulker;
import net.minecraft.server.v1_12_R1.EntityZombie;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class TestComponents {
    private Map<ConfigPath, CreatorHub> hubs = new Hashtable<ConfigPath, CreatorHub>() {{
        this.put(ConfigPath.HOTBAR_SLOTS, MinigamesDTools.getInstance().getHotbarItemCreatorHub());
        this.put(ConfigPath.HOTBAR, MinigamesDTools.getInstance().getHotbarCreatorHub());
        this.put(ConfigPath.SCENARIO_CHAIN, MinigamesDTools.getInstance().getScenarioChainCreatorHub());
        this.put(ConfigPath.SCENARIO, MinigamesDTools.getInstance().getScenarioCreatorHub());
        this.put(ConfigPath.ACTIVE_POINT_REACTIONS, MinigamesDTools.getInstance().getReactionCreatorHub());
        this.put(ConfigPath.ACTIVE_POINT_BEHAVIORS, MinigamesDTools.getInstance().getBehaviorCreatorHub());
        this.put(ConfigPath.ACTIVE_POINT, MinigamesDTools.getInstance().getActivePointsCreatorHub());
        this.put(ConfigPath.ARENA_LOBBY, MinigamesDTools.getInstance().getArenaLobbyCreatorHub());
        this.put(ConfigPath.INVENTORY_GUI, MinigamesDTools.getInstance().getInventoryGUICreatorHub());
        this.put(ConfigPath.INVENTORY_GUI_SLOT, MinigamesDTools.getInstance().getInventoryGuiSlotCreatorHub());
        this.put(ConfigPath.ARENA_FOLDER, MinigamesDTools.getInstance().getArenaCreatorHub());
        this.put(ConfigPath.SERVER_LOBBY, MinigamesDTools.getInstance().getLobbyCreatorHub());
        this.put(ConfigPath.CONDITIONS, MinigamesDTools.getInstance().getConditionsCreatorHub());
        this.put(ConfigPath.TEAMS, MinigamesDTools.getInstance().getTeamCreatorHub());
    }};

    private void linkCreators(ConfigPath path) {
        List<ConfigEntity> keys = MinigamesDTools.getInstance().getConfigProvider().getPoolContents(path);
        for(ConfigEntity configEntity : keys) {
            ConfigurationSection conf = MinigamesDTools.getInstance().getConfigProvider().getEntity(path, configEntity.getID()).getData();
            try {
                CreatorHub creatorHub = this.hubs.get(path);
                String configEntityId = configEntity.getID();
                String creatorId = conf.get("creator_id").toString();
                creatorHub.registerRouteId2Creator(configEntityId, creatorId);
            } catch (Exception e) {
                Debug.print(Debug.LEVEL.WARNING, "Error while link creator: " + configEntity.getID());
                e.printStackTrace();
            }
        }
    }

    public void load() {
        Debug.print(Debug.LEVEL.NOTICE, "###########################################");
        Debug.print(Debug.LEVEL.NOTICE, "########### load code for tests ###########");
        Debug.print(Debug.LEVEL.NOTICE, "#.........................................#");

        // hotbar slots
        try {
            MinigamesDTools.getInstance().getHotbarItemCreatorHub().registerCreator(new ExampleItemCreator());
            MinigamesDTools.getInstance().getHotbarItemCreatorHub().registerCreator(new ShowTeamSpectatorItemCreator());

            this.linkCreators(ConfigPath.HOTBAR_SLOTS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // hotbar
        try {
            MinigamesDTools.getInstance().getHotbarCreatorHub().registerCreator(new HeldHotbarCreator());
            MinigamesDTools.getInstance().getHotbarCreatorHub().registerCreator(new ItemInterractHotbarCreator());

            this.linkCreators(ConfigPath.HOTBAR);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // scenario chain
        try {
            MinigamesDTools.getInstance().getScenarioChainCreatorHub().registerCreator(new DefaultScenarioChainCreator());
            this.linkCreators(ConfigPath.SCENARIO_CHAIN);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // scenario
        try {
            MinigamesDTools.getInstance().getScenarioCreatorHub().registerCreator(new ExampleScenarioCreator());
            MinigamesDTools.getInstance().getScenarioCreatorHub().registerCreator(new ExampleTwoScenarioCreator());

            this.linkCreators(ConfigPath.SCENARIO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Entity controllers
        MinigamesDTools.getInstance().getEntityAPI().register("test", 69, EntityShulker.class, SilentShulker.class);
        MinigamesDTools.getInstance().getEntityAPI().register("my_zombie", 54, EntityZombie.class, SkyZombie.class);
        MinigamesDTools.getInstance().getEntityAPI().register("sky_dragon", 63, EntityEnderDragon.class, SkyDragon.class);

        // reaction
        try {
            MinigamesDTools.getInstance().getReactionCreatorHub().registerCreator(new TeamIncrementWinTicketsReactionCreator());
            MinigamesDTools.getInstance().getReactionCreatorHub().registerCreator(new ItemGiveReactionCreator());

            this.linkCreators(ConfigPath.ACTIVE_POINT_REACTIONS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // behavior
        try {
            MinigamesDTools.getInstance().getBehaviorCreatorHub().registerCreator(new ExampleBehaviorCreator());

            this.linkCreators(ConfigPath.ACTIVE_POINT_BEHAVIORS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // active_point
        try {
            MinigamesDTools.getInstance().getActivePointsCreatorHub().registerCreator(new ExampleActivePointsCreator());

            this.linkCreators(ConfigPath.ACTIVE_POINT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // team
        try {
            MinigamesDTools.getInstance().getTeamCreatorHub().registerCreator(new ExampleTeamCreator());

            this.linkCreators(ConfigPath.TEAMS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // lobby
        try {
            MinigamesDTools.getInstance().getArenaLobbyCreatorHub().registerCreator(new StarterLobbyCreator());
            MinigamesDTools.getInstance().getArenaLobbyCreatorHub().registerCreator(new ExampleRespawnLobbyCreator());
            MinigamesDTools.getInstance().getArenaLobbyCreatorHub().registerCreator(new ExampleSpectatorLobbyCreator());

            this.linkCreators(ConfigPath.ARENA_LOBBY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // inventory gui
        try {
            MinigamesDTools.getInstance().getInventoryGUICreatorHub().registerCreator(new DefaultViewInventoryCreator());
            this.linkCreators(ConfigPath.INVENTORY_GUI);

            MinigamesDTools.getInstance().getInventoryGuiSlotCreatorHub().registerCreator(new ExampleInventorySlotCreator());
            this.linkCreators(ConfigPath.INVENTORY_GUI_SLOT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // GUIProvider
        try {
            MinigamesDTools.getInstance().getGuiCreatorHub().registerCreator(new BossbarExampleCreator());
            MinigamesDTools.getInstance().getGuiCreatorHub().registerCreator(new ScoreboardExampleCreator());

            MinigamesDTools.getInstance().getGuiCreatorHub().registerRouteId2Creator("BossbarExample", "bossbar_example");
            MinigamesDTools.getInstance().getGuiCreatorHub().registerRouteId2Creator("ScoreboardExample", "scoreboard_example");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // arena
        try {
            MinigamesDTools.getInstance().getArenaCreatorHub().registerCreator(new ExampleArenaCreator());

            this.linkCreators(ConfigPath.ARENA_FOLDER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // server lobby
        try {
            MinigamesDTools.getInstance().getLobbyCreatorHub().registerCreator(new ExampleLobbyCreator());

            this.linkCreators(ConfigPath.SERVER_LOBBY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // conditions
        try {
            MinigamesDTools.getInstance().getConditionsCreatorHub().registerCreator(new ExampleConditionCreator());
            MinigamesDTools.getInstance().getConditionsCreatorHub().registerCreator(new EmptyInventoryConditionCreator());

            this.linkCreators(ConfigPath.CONDITIONS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
