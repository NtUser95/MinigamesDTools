package com.gmail.borlandlp.minigamesdtools.activepoints.behaviors.custom;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.activepoints.ActivePoint;
import com.gmail.borlandlp.minigamesdtools.config.ConfigManager;
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.Creator;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo;
import org.bukkit.configuration.ConfigurationSection;

@CreatorInfo(creatorId = "example_behavior")
public class ExampleBehaviorCreator implements Creator {
    @Override
    public Object create(String Id, AbstractDataProvider dataProvider) throws Exception {
        ExampleBehavior exampleBehavior = new ExampleBehavior();
        ConfigurationSection conf = MinigamesDTools.getInstance().getConfigManager().getConfigSection(ConfigManager.ConfigPath.ACTIVE_POINT_BEHAVIORS, Id);
        if(conf == null) {
            throw new Exception("Cant find config for Behavior[ID:" + Id + "]");
        }

        exampleBehavior.setActivePoint((ActivePoint) dataProvider.get("active_point_instance"));
        exampleBehavior.setName(Id);
        exampleBehavior.setDelay(Integer.parseInt(conf.get("delay").toString()));
        exampleBehavior.setPeriod(Integer.parseInt(conf.get("period").toString()));

        return exampleBehavior;
    }
}
