package com.gmail.borlandlp.minigamesdtools.gui.hotbar.type;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath;
import com.gmail.borlandlp.minigamesdtools.creator.*;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.Hotbar;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.SlotItem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.*;

@CreatorInfo(creatorId = "held_hotbar")
public class HeldHotbarCreator implements Creator {
    public List<String> getDataProviderRequiredFields() {
        return Arrays.asList("player");
    }

    @Override
    public Hotbar create(String hotbarID, AbstractDataProvider dataProvider) throws Exception {
        ConfigurationSection hotbarCfg = MinigamesDTools.getInstance().getConfigProvider().getEntity(ConfigPath.HOTBAR, hotbarID).getData();
        if(hotbarCfg == null) {
            throw new Exception("cant find config file for hotbar[ID:" + hotbarID + "]");
        }

        HeldHotbar hotbar = new HeldHotbar((Player) dataProvider.get("player"));

        for(String key : hotbarCfg.getConfigurationSection("slots").getKeys(false)) {
            int slotIndex = Integer.parseInt(key);
            String slotID = hotbarCfg.get("slots." + key).toString();
            SlotItem slotItem = null;
            if(MinigamesDTools.getInstance().getHotbarItemCreatorHub().containsRouteId2Creator(slotID)) {
                slotItem = MinigamesDTools.getInstance().getHotbarItemCreatorHub().createHotbarItem(slotID, new DataProvider());
                if(slotItem != null) {
                    hotbar.setSlot(slotIndex, slotItem);
                } else {
                    throw new Exception("Detected a factory problem for HotbarSlotConfig[ID:" + slotID + "] for HotbarConfig[ID:" + hotbarID + "]");
                }
            } else {
                throw new Exception("Detected problem for HotbarSlotConfig[ID:" + slotID + "] for HotbarConfig[ID:" + hotbarID + "]");
            }
        }

        return hotbar;
    }
}
