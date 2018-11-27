package com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.gun;

import com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.SlotItem;
import org.bukkit.entity.Player;

public class SimplyGun extends SlotItem {
    @Override
    public boolean use(Player player) {
        return false;
    }
}
