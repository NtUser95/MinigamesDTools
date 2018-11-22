package com.gmail.borlandlp.minigamesdtools.arena.gui.hotbar;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaEventListener;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaEventHandler;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerJoinLocalEvent;
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerLeaveLocalEvent;
import org.bukkit.event.Listener;

public class HotbarListener implements Listener, ArenaEventListener {
    private HotbarController hotbarController;

    public HotbarListener(HotbarController controller) {
        this.hotbarController = controller;
    }

    @ArenaEventHandler
    public void onPlayerJoin(ArenaPlayerJoinLocalEvent event) {
        if(hotbarController.isEnabled()) {
            try {
                MinigamesDTools.getInstance().getHotbarAPI().bindHotbar(this.hotbarController.buildDefaultHotbarFor(event.getPlayer()), event.getPlayer());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @ArenaEventHandler
    public void onPlayerLeave(ArenaPlayerLeaveLocalEvent event) {
        if(this.hotbarController.isEnabled()) {
            MinigamesDTools.getInstance().getHotbarAPI().unbindHotbar(event.getPlayer());
        }
    }
}