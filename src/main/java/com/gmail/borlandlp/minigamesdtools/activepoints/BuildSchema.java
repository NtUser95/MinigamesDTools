package com.gmail.borlandlp.minigamesdtools.activepoints;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class BuildSchema {
    private Map<Location, Material> blocks = new HashMap<>();

    public BuildSchema(Map<Location, Material> map) {
        this.blocks = map;
    }

    public Map<Location, Material> getSchema() {
        return blocks;
    }
}
