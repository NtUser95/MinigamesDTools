package com.gmail.borlandlp.minigamesdtools.arena.chunkloader;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaComponent;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent;
import org.bukkit.Chunk;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.Map;

public class ChunkLoaderController extends ArenaComponent implements ArenaPhaseComponent {
    private Map<String, ChunksLoader> loaders;
    private Listener listener;

    public boolean containsChunk(Chunk chunk) {
        for (ChunksLoader loader : this.getLoaders().values()) {
            if(loader.getChunks().contains(chunk)) return true;
        }

        return false;
    }

    public Map<String, ChunksLoader> getLoaders() {
        return loaders;
    }

    public void setLoaders(Map<String, ChunksLoader> loaders) {
        this.loaders = loaders;
    }

    @Override
    public void onInit() {
        this.listener = new ChunksListener(this);
        MinigamesDTools.getInstance().getServer().getPluginManager().registerEvents(this.listener, MinigamesDTools.getInstance());
        for (ArenaPhaseComponent component : this.getLoaders().values()) {
            this.getArena().getPhaseComponentController().register(component);
        }
    }

    @Override
    public void beforeGameStarting() {

    }

    @Override
    public void gameEnded() {
        HandlerList.unregisterAll(this.listener);
    }

    @Override
    public void update() {

    }

    @Override
    public void beforeRoundStarting() {

    }

    @Override
    public void onRoundEnd() {

    }
}
