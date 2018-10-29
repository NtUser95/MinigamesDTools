package com.gmail.borlandlp.minigamesdtools.activepoints.type;

import com.gmail.borlandlp.minigamesdtools.activepoints.BuildSchema;
import com.gmail.borlandlp.minigamesdtools.util.GeometryHelper;
import com.gmail.borlandlp.minigamesdtools.util.geometry.GeneratedBlockSchema;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class SquareBlockPoint extends StaticBlockPoint {
    @Override
    public BuildSchema getBuildSchema() {
        GeneratedBlockSchema generatedBlockSchema = GeometryHelper.generateSquare(this.getLocation(), this.getRadius(), this.isHollow());
        Map<Location, Material> blocks = new HashMap<>();
        for(Location location : generatedBlockSchema.getBoundBlocks()) {
            blocks.put(location, Material.DIRT);
        }
        for(Location location : generatedBlockSchema.getFilledBlocks()) {
            blocks.put(location, Material.STONE);
        }

        return new BuildSchema(blocks);
    }
}
