package com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.inventory_gui;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.config.ConfigEntity;
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath;
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.Creator;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@CreatorInfo(creatorId = "inventory_page_open_requester")
public class InventoryPageOpenRequesterCreator implements Creator {
    @Override
    public List<String> getDataProviderRequiredFields() {
        return new ArrayList<>();
    }

    @Override
    public InventoryPageOpenRequester create(String ID, AbstractDataProvider dataProvider) throws Exception {
        InventoryPageOpenRequester requester = new InventoryPageOpenRequester();
        ConfigEntity configEntity = MinigamesDTools.getInstance().getConfigProvider().getEntity(ConfigPath.HOTBAR_SLOTS, ID);
        ConfigurationSection configurationSection = configEntity.getData();

        if(dataProvider.contains("page_id")) {
            requester.setPageId((String) dataProvider.get("page_id"));
        } else {
            requester.setPageId(configEntity.getData().get("page_id").toString());
        }

        ItemStack activeIcon = new ItemStack(Material.getMaterial(configurationSection.get("active_icon").toString()));
        requester.setActiveIcon(activeIcon);
        ItemStack unactiveIcon = new ItemStack(Material.getMaterial(configurationSection.get("unactive_icon").toString()));
        requester.setUnactiveIcon(unactiveIcon);
        requester.setCooldownTime(Integer.parseInt(configurationSection.get("cooldown").toString()));
        requester.setInfiniteSlot(Boolean.parseBoolean(configurationSection.get("infinite").toString()));
        requester.setAmount(Integer.parseInt(configurationSection.get("amount").toString()));

        return requester;
    }
}
