package com.gmail.borlandlp.minigamesdtools.test.gui.inventory;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.arena.team.TeamProvider;
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.Creator;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo;
import com.gmail.borlandlp.minigamesdtools.gui.inventory.DefaultViewInventory;
import com.gmail.borlandlp.minigamesdtools.gui.inventory.DrawableInventory;
import com.gmail.borlandlp.minigamesdtools.test.gui.inventory.slots.ArenaSwitchSpectateTargetSlot;
import com.gmail.borlandlp.minigamesdtools.gui.inventory.slots.InventorySlot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.SkullMeta;

@CreatorInfo(creatorId = "spectator_switch_target_inventory")
public class SpectatorSwitchTargetInventoryFactory implements Creator {
    @Override
    public DrawableInventory create(String ID, AbstractDataProvider dataProvider) throws Exception {
        DefaultViewInventory inventory = new DefaultViewInventory();
        Player p = (Player) dataProvider.get("player");

        TeamProvider team = MinigamesDTools.getInstance().getArenaAPI().getArenaOf(p).getTeamController().getTeamOf(p);
        inventory.setRows((int) (Math.ceil(team.getPlayers().size() / 9D) * 9D));

        int i = 0;
        for (Player player : team.getPlayers()) {
            InventorySlot slot = new ArenaSwitchSpectateTargetSlot();
            slot.setMaterial(Material.SKULL_ITEM);
            slot.setAmount(1);
            slot.setDamage((short) 3);
            SkullMeta meta = (SkullMeta) slot.build().getItemMeta();
            meta.setOwningPlayer(player);
            meta.setDisplayName(player.getName());
            slot.setMeta(meta);

            inventory.setSlot(i++, slot);
        }


        return inventory;
    }
}
