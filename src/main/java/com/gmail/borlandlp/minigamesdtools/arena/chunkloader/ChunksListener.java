package com.gmail.borlandlp.minigamesdtools.arena.chunkloader;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

public class ChunksListener implements Listener {
    private ChunkLoaderController chunkLoaderController;

    public ChunksListener(ChunkLoaderController controller) {
        this.chunkLoaderController = controller;
    }

    public ChunkLoaderController getChunkLoaderController() {
        return chunkLoaderController;
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        if(this.getChunkLoaderController().containsChunk(event.getChunk())) {
            event.setCancelled(true);
        }
    }
}
