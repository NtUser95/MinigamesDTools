package com.gmail.borlandlp.minigamesdtools.arena.commands;

import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public interface ArenaCommandWatcher extends ArenaPhaseComponent {
    void onCommand(PlayerCommandPreprocessEvent event);
}
