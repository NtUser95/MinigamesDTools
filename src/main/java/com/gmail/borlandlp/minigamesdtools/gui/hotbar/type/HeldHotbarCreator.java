package com.gmail.borlandlp.minigamesdtools.gui.hotbar.type;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.config.ConfigManager;
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.Creator;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo;
import com.gmail.borlandlp.minigamesdtools.creator.DataProvider;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.Hotbar;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.SlotItem;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.type.HeldHotbar;
import org.bukkit.configuration.ConfigurationSection;

@CreatorInfo(creatorId = "held_hotbar")
public class HeldHotbarCreator implements Creator {
    @Override
    public Hotbar create(String hotbarID, AbstractDataProvider dataProvider) throws Exception {
        ConfigurationSection hotbarCfg = MinigamesDTools.getInstance().getConfigManager().getConfigSection(ConfigManager.ConfigPath.HOTBAR, hotbarID);
        if(hotbarCfg == null) {
            throw new Exception("cant find config file for hotbar[ID:" + hotbarID + "]");
        }

        HeldHotbar hotbar = new HeldHotbar();

        for(String key : hotbarCfg.getConfigurationSection("slots").getKeys(false)) {
            int slotIndex = Integer.parseInt(key);
            String slotID = hotbarCfg.get("slots." + key).toString();
            SlotItem slotItem = null;
            if(MinigamesDTools.getInstance().getHotbarItemCreatorHub().containsRouteId2Creator(slotID)) {
                slotItem = MinigamesDTools.getInstance().getHotbarItemCreatorHub().createHotbarItem(slotID, new DataProvider());
                hotbar.setSlot(slotIndex, slotItem);
            } else {
                throw new Exception("detected problem for HotbarSlotConfig[ID:" + slotID + "] for HotbarConfig[ID:" + hotbarID + "]");
            }
        }

        return hotbar;
    }
}
