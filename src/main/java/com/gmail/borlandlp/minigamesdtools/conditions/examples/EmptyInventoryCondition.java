package com.gmail.borlandlp.minigamesdtools.conditions.examples;

import com.gmail.borlandlp.minigamesdtools.conditions.AbstractCondition;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EmptyInventoryCondition extends AbstractCondition {
    @Override
    public boolean isValidPlayer(Player p) {
        for (ItemStack itemStack : p.getInventory().getContents()) {
            if(itemStack != null && itemStack.getType() != Material.AIR) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String getErrorId() {
        return "non_empty_inventory_error";
    }
}
