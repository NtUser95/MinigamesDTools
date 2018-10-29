package com.gmail.borlandlp.minigamesdtools.activepoints.type;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.activepoints.BuildSchema;
import com.gmail.borlandlp.minigamesdtools.util.GeometryHelper;
import com.gmail.borlandlp.minigamesdtools.util.geometry.GeneratedBlockSchema;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class SphereBlockPoint extends StaticBlockPoint {
    @Override
    public BuildSchema getBuildSchema() {
        Debug.print(Debug.LEVEL.NOTICE, "build schema for sphere " + this.getLocation() + "|" + this.getRadius() + "|" + this.isHollow());
        GeneratedBlockSchema generatedBlockSchema = GeometryHelper.generateSphere(this.getLocation(), this.getRadius(), this.isHollow());
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
