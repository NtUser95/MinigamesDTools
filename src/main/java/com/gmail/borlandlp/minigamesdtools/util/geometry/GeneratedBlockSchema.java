package com.gmail.borlandlp.minigamesdtools.util.geometry;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class GeneratedBlockSchema {
    private List<Location> boundBlocks;
    private List<Location> filledBlocks;

    public GeneratedBlockSchema(List<Location> boundBlocks, List<Location> filledBlocks) {
        this.boundBlocks = boundBlocks;
        this.filledBlocks = filledBlocks;
    }

    public List<Location> getBoundBlocks() {
        return boundBlocks;
    }

    public List<Location> getFilledBlocks() {
        return filledBlocks;
    }

    public List<Location> getAllBlocks() {
        List<Location> copyBound = new ArrayList<>(this.boundBlocks);//clone Dolly!
        copyBound.addAll(this.filledBlocks);
        return copyBound;
    }
}
