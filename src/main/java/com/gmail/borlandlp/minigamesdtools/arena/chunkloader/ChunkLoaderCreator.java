package com.gmail.borlandlp.minigamesdtools.arena.chunkloader;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class ChunkLoaderCreator {
    public ChunksLoader buildChunkLoader(World world, Location min_point, Location max_point) throws Exception {
        if(min_point.getX() > max_point.getX() || min_point.getZ() > max_point.getZ()) { // Swap locations if programmer was mistaken
            Location buff = min_point.clone();
            min_point = max_point;
            max_point = buff;
        }

        List<Chunk> chunks = new ArrayList<>();
        for (int x = min_point.getBlockX(); x <= max_point.getBlockX(); x += 16) {
            for (int z = min_point.getBlockZ(); z <= max_point.getBlockZ(); z += 16) {
                Chunk chunk = world.getChunkAt(x, z);
                if(chunk != null) {
                    chunks.add(chunk);
                } else {
                    throw new Exception("Error while parsing chunks for min_point:" + min_point + " # max_point:" + max_point);
                }
            }
        }

        ChunksLoader chunksLoader = new ChunksLoader();
        chunksLoader.setChunks(chunks);

        return chunksLoader;
    }
}
