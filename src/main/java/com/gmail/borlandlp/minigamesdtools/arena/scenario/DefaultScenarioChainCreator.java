package com.gmail.borlandlp.minigamesdtools.arena.scenario;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase;
import com.gmail.borlandlp.minigamesdtools.config.ConfigManager;
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.Creator;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CreatorInfo(creatorId = "default_scenario_chain")
public class DefaultScenarioChainCreator implements Creator {
    @Override
    public Object create(String ID, AbstractDataProvider dataProvider) throws Exception {
        ConfigurationSection configurationSection = MinigamesDTools.getInstance().getConfigManager().getConfigSection(ConfigManager.ConfigPath.SCENARIO_CHAIN, ID);
        if(configurationSection == null) {
            throw new Exception("Cant find config for ScenarioChain[ID:" + ID + "]");
        }

        //load scenarios
        Map<String, Scenario> scenarioMap = new HashMap<>();
        for (String scenarioPhase : configurationSection.getConfigurationSection("chain").getKeys(false)) {
            String scenarioID = configurationSection.get("chain." + scenarioPhase + ".scenario_id").toString();
            Scenario scenario = MinigamesDTools.getInstance().getScenarioCreatorHub().createScenario(scenarioID, dataProvider);
            if(scenario != null) {
                scenarioMap.put(scenarioPhase, scenario);
            } else {
                throw new Exception("internal error while build Scenario.");
            }
        }

        // add parents for scenarios
        for (String scenarioPhase : configurationSection.getConfigurationSection("chain").getKeys(false)) {
            List<String> parents = configurationSection.getStringList("chain." + scenarioPhase + ".parent");
            ScenarioAbstract scenarioAbstract = (ScenarioAbstract) scenarioMap.get(scenarioPhase);
            if(parents != null && parents.size() > 0) {
                for (String parentID : parents) {
                    if(scenarioMap.containsKey(parentID)) {
                        scenarioAbstract.addParent(scenarioMap.get(parentID));
                    } else {
                        throw new Exception("invalid parentScenarioID");
                    }
                }
            }
        }

        ScenarioController scenarioController = new ScenarioController((ArenaBase) dataProvider.get("arena_instance"));
        scenarioController.setScenarios(new ArrayList<>(scenarioMap.values()));

        return scenarioController;
    }
}
