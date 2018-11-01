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
import com.gmail.borlandlp.minigamesdtools.config.ConfigManager;
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
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Set;

public class TestComponents {
    public void load() {
        Debug.print(Debug.LEVEL.NOTICE, "###########################################");
        Debug.print(Debug.LEVEL.NOTICE, "########### load code for tests ###########");
        Debug.print(Debug.LEVEL.NOTICE, "#.........................................#");

        // hotbar slots
        try {
            MinigamesDTools.getInstance().getHotbarItemCreatorHub().registerCreator(new ExampleItemCreator());
            MinigamesDTools.getInstance().getHotbarItemCreatorHub().registerCreator(new ShowTeamSpectatorItemCreator());

            // привязываем описываемые предметы в конфигах к их сборщикам-создателям.
            Set<String> keys = MinigamesDTools.getInstance().getConfigManager().getConfig(ConfigManager.ConfigPath.HOTBAR_SLOTS).getKeys(false);
            for(String hotbarSlotID : keys) {
                ConfigurationSection conf = MinigamesDTools.getInstance().getConfigManager().getConfigSection(ConfigManager.ConfigPath.HOTBAR_SLOTS, hotbarSlotID);
                MinigamesDTools.getInstance().getHotbarItemCreatorHub().registerRouteId2Creator(hotbarSlotID, conf.get("creator_id").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // hotbar
        try {
            MinigamesDTools.getInstance().getHotbarCreatorHub().registerCreator(new HeldHotbarCreator());
            MinigamesDTools.getInstance().getHotbarCreatorHub().registerCreator(new ItemInterractHotbarCreator());

            // привязываем описываемые предметы в конфигах к их сборщикам-создателям.
            Set<String> keys = MinigamesDTools.getInstance().getConfigManager().getConfig(ConfigManager.ConfigPath.HOTBAR).getKeys(false);
            for(String hotbarSlotID : keys) {
                ConfigurationSection conf = MinigamesDTools.getInstance().getConfigManager().getConfigSection(ConfigManager.ConfigPath.HOTBAR, hotbarSlotID);
                MinigamesDTools.getInstance().getHotbarCreatorHub().registerRouteId2Creator(hotbarSlotID, conf.get("creator_id").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // scenario chain
        try {
            MinigamesDTools.getInstance().getScenarioChainCreatorHub().registerCreator(new DefaultScenarioChainCreator());

            // привязываем описываемые предметы в конфигах к их сборщикам-создателям.
            Set<String> keys = MinigamesDTools.getInstance().getConfigManager().getConfig(ConfigManager.ConfigPath.SCENARIO_CHAIN).getKeys(false);
            for(String key : keys) {
                ConfigurationSection conf = MinigamesDTools.getInstance().getConfigManager().getConfigSection(ConfigManager.ConfigPath.SCENARIO_CHAIN, key);
                MinigamesDTools.getInstance().getScenarioChainCreatorHub().registerRouteId2Creator(key, conf.get("creator_id").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // scenario
        try {
            MinigamesDTools.getInstance().getScenarioCreatorHub().registerCreator(new ExampleScenarioCreator());
            MinigamesDTools.getInstance().getScenarioCreatorHub().registerCreator(new ExampleTwoScenarioCreator());

            // привязываем описываемые предметы в конфигах к их сборщикам-создателям.
            Set<String> keys = MinigamesDTools.getInstance().getConfigManager().getConfig(ConfigManager.ConfigPath.SCENARIO).getKeys(false);
            for(String key : keys) {
                ConfigurationSection conf = MinigamesDTools.getInstance().getConfigManager().getConfigSection(ConfigManager.ConfigPath.SCENARIO, key);
                MinigamesDTools.getInstance().getScenarioCreatorHub().registerRouteId2Creator(key, conf.get("creator_id").toString());
            }
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

            // привязываем описываемые предметы в конфигах к их сборщикам-создателям.
            Set<String> keys = MinigamesDTools.getInstance().getConfigManager().getConfig(ConfigManager.ConfigPath.ACTIVE_POINT_REACTIONS).getKeys(false);
            for(String key : keys) {
                ConfigurationSection conf = MinigamesDTools.getInstance().getConfigManager().getConfigSection(ConfigManager.ConfigPath.ACTIVE_POINT_REACTIONS, key);
                MinigamesDTools.getInstance().getReactionCreatorHub().registerRouteId2Creator(key, conf.get("creator_id").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // behavior
        try {
            MinigamesDTools.getInstance().getBehaviorCreatorHub().registerCreator(new ExampleBehaviorCreator());

            Set<String> keys = MinigamesDTools.getInstance().getConfigManager().getConfig(ConfigManager.ConfigPath.ACTIVE_POINT_BEHAVIORS).getKeys(false);
            for(String key : keys) {
                ConfigurationSection conf = MinigamesDTools.getInstance().getConfigManager().getConfigSection(ConfigManager.ConfigPath.ACTIVE_POINT_BEHAVIORS, key);
                MinigamesDTools.getInstance().getBehaviorCreatorHub().registerRouteId2Creator(key, conf.get("creator_id").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // active_point
        try {
            MinigamesDTools.getInstance().getActivePointsCreatorHub().registerCreator(new ExampleActivePointsCreator());

            Set<String> keys = MinigamesDTools.getInstance().getConfigManager().getConfig(ConfigManager.ConfigPath.ACTIVE_POINT).getKeys(false);
            for(String key : keys) {
                ConfigurationSection conf = MinigamesDTools.getInstance().getConfigManager().getConfigSection(ConfigManager.ConfigPath.ACTIVE_POINT, key);
                MinigamesDTools.getInstance().getActivePointsCreatorHub().registerRouteId2Creator(key, conf.get("creator_id").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // team
        try {
            MinigamesDTools.getInstance().getTeamCreatorHub().registerCreator(new ExampleTeamCreator());

            Set<String> keys = MinigamesDTools.getInstance().getConfigManager().getConfig(ConfigManager.ConfigPath.TEAMS).getKeys(false);
            for(String key : keys) {
                ConfigurationSection conf = MinigamesDTools.getInstance().getConfigManager().getConfigSection(ConfigManager.ConfigPath.TEAMS, key);
                MinigamesDTools.getInstance().getTeamCreatorHub().registerRouteId2Creator(key, conf.get("creator_id").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // lobby
        try {
            MinigamesDTools.getInstance().getArenaLobbyCreatorHub().registerCreator(new StarterLobbyCreator());
            MinigamesDTools.getInstance().getArenaLobbyCreatorHub().registerCreator(new ExampleRespawnLobbyCreator());
            MinigamesDTools.getInstance().getArenaLobbyCreatorHub().registerCreator(new ExampleSpectatorLobbyCreator());

            Set<String> keys = MinigamesDTools.getInstance().getConfigManager().getConfig(ConfigManager.ConfigPath.STARTER_LOBBY).getKeys(false);
            for(String key : keys) {
                ConfigurationSection conf = MinigamesDTools.getInstance().getConfigManager().getConfigSection(ConfigManager.ConfigPath.STARTER_LOBBY, key);
                MinigamesDTools.getInstance().getArenaLobbyCreatorHub().registerRouteId2Creator(key, conf.get("creator_id").toString());
            }

            keys = MinigamesDTools.getInstance().getConfigManager().getConfig(ConfigManager.ConfigPath.RESPAWN_LOBBY).getKeys(false);
            for(String key : keys) {
                ConfigurationSection conf = MinigamesDTools.getInstance().getConfigManager().getConfigSection(ConfigManager.ConfigPath.RESPAWN_LOBBY, key);
                MinigamesDTools.getInstance().getArenaLobbyCreatorHub().registerRouteId2Creator(key, conf.get("creator_id").toString());
            }

            keys = MinigamesDTools.getInstance().getConfigManager().getConfig(ConfigManager.ConfigPath.SPECTATOR_LOBBY).getKeys(false);
            for(String key : keys) {
                ConfigurationSection conf = MinigamesDTools.getInstance().getConfigManager().getConfigSection(ConfigManager.ConfigPath.SPECTATOR_LOBBY, key);
                MinigamesDTools.getInstance().getArenaLobbyCreatorHub().registerRouteId2Creator(key, conf.get("creator_id").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // inventory gui
        try {
            MinigamesDTools.getInstance().getInventoryGUICreatorHub().registerCreator(new DefaultViewInventoryCreator());
            Set<String> keys = MinigamesDTools.getInstance().getConfigManager().getConfig(ConfigManager.ConfigPath.INVENTORY_GUI).getKeys(false);
            for(String key : keys) {
                ConfigurationSection conf = MinigamesDTools.getInstance().getConfigManager().getConfigSection(ConfigManager.ConfigPath.INVENTORY_GUI, key);
                MinigamesDTools.getInstance().getInventoryGUICreatorHub().registerRouteId2Creator(key, conf.get("creator_id").toString());
            }

            MinigamesDTools.getInstance().getInventoryGuiSlotCreatorHub().registerCreator(new ExampleInventorySlotCreator());
            keys = MinigamesDTools.getInstance().getConfigManager().getConfig(ConfigManager.ConfigPath.INVENTORY_GUI_SLOT).getKeys(false);
            for(String key : keys) {
                ConfigurationSection conf = MinigamesDTools.getInstance().getConfigManager().getConfigSection(ConfigManager.ConfigPath.INVENTORY_GUI_SLOT, key);
                MinigamesDTools.getInstance().getInventoryGuiSlotCreatorHub().registerRouteId2Creator(key, conf.get("creator_id").toString());
            }
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

            for(String arenaID : MinigamesDTools.getInstance().getConfigManager().getArenasName()) {
                YamlConfiguration conf = MinigamesDTools.getInstance().getConfigManager().getArenaConfig(arenaID);
                MinigamesDTools.getInstance().getArenaCreatorHub().registerRouteId2Creator(arenaID, conf.get("creator_id").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // server lobby
        try {
            MinigamesDTools.getInstance().getLobbyCreatorHub().registerCreator(new ExampleLobbyCreator());

            Set<String> keys = MinigamesDTools.getInstance().getConfigManager().getConfig(ConfigManager.ConfigPath.SERVER_LOBBY).getKeys(false);
            for(String key : keys) {
                ConfigurationSection conf = MinigamesDTools.getInstance().getConfigManager().getConfigSection(ConfigManager.ConfigPath.SERVER_LOBBY, key);
                MinigamesDTools.getInstance().getLobbyCreatorHub().registerRouteId2Creator(key, conf.get("creator_id").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // conditions
        try {
            MinigamesDTools.getInstance().getConditionsCreatorHub().registerCreator(new ExampleConditionCreator());
            MinigamesDTools.getInstance().getConditionsCreatorHub().registerCreator(new EmptyInventoryConditionCreator());

            Set<String> keys = MinigamesDTools.getInstance().getConfigManager().getConfig(ConfigManager.ConfigPath.CONDITIONS).getKeys(false);
            for(String key : keys) {
                ConfigurationSection conf = MinigamesDTools.getInstance().getConfigManager().getConfigSection(ConfigManager.ConfigPath.CONDITIONS, key);
                MinigamesDTools.getInstance().getConditionsCreatorHub().registerRouteId2Creator(key, conf.get("creator_id").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
