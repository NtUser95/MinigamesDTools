package com.gmail.borlandlp.minigamesdtools.arena.commands;

import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase;
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.Creator;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo;

import java.util.Arrays;
import java.util.List;

@CreatorInfo(creatorId = "default_command_watcher")
public class DefaultWatcherCreator implements Creator {
    @Override
    public List<String> getDataProviderRequiredFields() {
        return Arrays.asList("blacklist_rules", "whitelist_rules", "arena_instance");
    }

    @Override
    public DefaultWatcher create(String ID, AbstractDataProvider dataProvider) throws Exception {
        DefaultWatcher watcher = new DefaultWatcher();
        watcher.setBlacklisted((List<String[]>) dataProvider.get("blacklist_rules"));
        watcher.setWhitelisted((List<String[]>) dataProvider.get("whitelist_rules"));
        watcher.setArena((ArenaBase) dataProvider.get("arena_instance"));

        return watcher;
    }
}