package com.gmail.borlandlp.minigamesdtools.test.gui.inventory.slots;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.events.ArenaRequestSpectatePlayer2Player;
import com.gmail.borlandlp.minigamesdtools.gui.inventory.slots.AbstractSlot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.SkullMeta;

public class ArenaSwitchSpectateTargetSlot extends AbstractSlot {
    @Override
    public void performClick(Player player) {
        Player playerTarget = ((SkullMeta) this.getMeta()).getOwningPlayer().getPlayer();
        ArenaRequestSpectatePlayer2Player event = new ArenaRequestSpectatePlayer2Player(player, playerTarget);
        Bukkit.getServer().getPluginManager().callEvent(event);
        Debug.print(Debug.LEVEL.NOTICE, "Switch spectate target for " + player.getName() + " to " + playerTarget.getName());
    }
}
