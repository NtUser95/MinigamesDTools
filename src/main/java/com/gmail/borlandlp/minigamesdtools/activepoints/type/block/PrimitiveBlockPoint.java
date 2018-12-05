package com.gmail.borlandlp.minigamesdtools.activepoints.type.block;

import org.bukkit.Material;

public abstract class PrimitiveBlockPoint extends StaticBlockPoint {
    private boolean hollow;
    private Material borderMaterial;
    private Material fillerMaterial;

    public Material getBorderMaterial() {
        return this.borderMaterial;
    }

    public void setBorderMaterial(Material material) {
        this.borderMaterial = material;
    }

    public Material getFillerMaterial() {
        return this.fillerMaterial;
    }

    public void setFillerMaterial(Material material) {
        this.fillerMaterial = material;
    }

    public boolean isHollow() {
        return hollow;
    }

    public void setHollow(boolean hollow) {
        this.hollow = hollow;
    }
}
