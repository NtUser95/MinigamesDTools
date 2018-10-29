package com.gmail.borlandlp.minigamesdtools.gui.hotbar.api;

import com.gmail.borlandlp.minigamesdtools.gui.hotbar.Hotbar;
import org.bukkit.entity.Player;

import java.util.Map;

public interface HotbarAPI {
    void bindHotbar(Hotbar hotbar, Player player);
    void unbindHotbar(Player player);
    boolean isBindedPlayer(Player player);
    Hotbar getHotbar(Player player);
    Map<Player, Hotbar> getAll();
}
