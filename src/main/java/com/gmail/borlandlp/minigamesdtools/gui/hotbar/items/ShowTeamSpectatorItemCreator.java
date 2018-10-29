package com.gmail.borlandlp.minigamesdtools.gui.hotbar.items;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.config.ConfigManager;
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.Creator;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

@CreatorInfo(creatorId = "show_team_spectator_item")
public class ShowTeamSpectatorItemCreator implements Creator {
    @Override
    public Object create(String ID, AbstractDataProvider dataProvider) throws Exception {
        ShowTeamSpectatorItem slotItem = new ShowTeamSpectatorItem();
        ConfigurationSection configurationSection = MinigamesDTools.getInstance().getConfigManager().getConfigSection(ConfigManager.ConfigPath.HOTBAR_SLOTS, ID);

        ItemStack activeIcon = new ItemStack(Material.getMaterial(configurationSection.get("active_icon").toString()));
        slotItem.setActiveIcon(activeIcon);
        ItemStack unactiveIcon = new ItemStack(Material.getMaterial(configurationSection.get("unactive_icon").toString()));
        slotItem.setUnactiveIcon(unactiveIcon);
        slotItem.setCooldownTime(Integer.parseInt(configurationSection.get("cooldown").toString()));
        slotItem.setInfiniteSlot(Boolean.parseBoolean(configurationSection.get("infinite").toString()));
        slotItem.setAmount(Integer.parseInt(configurationSection.get("amount").toString()));

        return slotItem;
    }
}
