package com.gmail.borlandlp.minigamesdtools.activepoints.type.block;

import com.gmail.borlandlp.minigamesdtools.activepoints.BuildSchema;
import com.gmail.borlandlp.minigamesdtools.util.GeometryHelper;
import com.gmail.borlandlp.minigamesdtools.util.geometry.GeneratedBlockSchema;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class FlatSquarePoint extends PrimitiveBlockPoint {
    @Override
    public BuildSchema getBuildSchema() {
        GeneratedBlockSchema generatedBlockSchema = GeometryHelper.generateFlatSquare(this.getLocation(), this.getRadius(), this.isHollow());
        Map<Location, Material> blocks = new HashMap<>();
        generatedBlockSchema.getBorderBlocks().forEach(location -> blocks.put(location, this.getBorderMaterial()));
        generatedBlockSchema.getFillerBlocks().forEach(location -> blocks.put(location, this.getFillerMaterial()));

        return new BuildSchema(blocks);
    }
}
