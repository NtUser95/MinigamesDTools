package com.gmail.borlandlp.minigamesdtools.arena.commands;

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub;

public class CommandWatcherCreatorHub extends CreatorHub {
    public ArenaCommandWatcher createCommandWatcher(String ID, AbstractDataProvider dataProvider) throws Exception {
        return (ArenaCommandWatcher) this.create(ID, dataProvider);
    }
}
