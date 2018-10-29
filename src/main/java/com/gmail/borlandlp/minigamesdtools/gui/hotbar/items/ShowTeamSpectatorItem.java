package com.gmail.borlandlp.minigamesdtools.gui.hotbar.items;

import com.gmail.borlandlp.minigamesdtools.events.PlayerRequestOpenInvGUIEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ShowTeamSpectatorItem extends SlotItem  {
    @Override
    public boolean use(Player player) {
        PlayerRequestOpenInvGUIEvent event = new PlayerRequestOpenInvGUIEvent(player, "spectator_switch_target");
        Bukkit.getPluginManager().callEvent(event);
        System.out.println("show spectator HUD!");
        return true;
    }
}
