package com.gmail.borlandlp.minigamesdtools.arena.chunkloader;

import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent;
import org.bukkit.Chunk;

import java.util.ArrayList;
import java.util.List;

public class ChunksLoader implements ArenaPhaseComponent {
    private List<Chunk> chunks = new ArrayList<>();

    public List<Chunk> getChunks() {
        return chunks;
    }

    public void setChunks(List<Chunk> chunks) {
        this.chunks = chunks;
    }

    @Override
    public void onInit() {

    }

    @Override
    public void beforeGameStarting() {
        for (Chunk chunk : this.getChunks()) {
            if(!chunk.isLoaded()) {
                chunk.load();
            }
        }
    }

    @Override
    public void gameEnded() {

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
