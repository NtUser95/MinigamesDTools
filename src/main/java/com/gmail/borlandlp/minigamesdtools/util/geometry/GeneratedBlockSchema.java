package com.gmail.borlandlp.minigamesdtools.util.geometry;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class GeneratedBlockSchema {
    private List<Location> borderBlocks;
    private List<Location> fillerBlocks;

    public GeneratedBlockSchema(List<Location> borderBlocks, List<Location> fillerBlocks) {
        this.borderBlocks = borderBlocks;
        this.fillerBlocks = fillerBlocks;
    }

    public List<Location> getBorderBlocks() {
        return this.borderBlocks;
    }

    public List<Location> getFillerBlocks() {
        return this.fillerBlocks;
    }

    public List<Location> getAllBlocks() {
        List<Location> copyBound = new ArrayList<>(this.borderBlocks);//clone Dolly!
        copyBound.addAll(this.fillerBlocks);
        return copyBound;
    }
}
