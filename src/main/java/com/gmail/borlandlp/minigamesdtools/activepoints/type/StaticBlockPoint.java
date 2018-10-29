package com.gmail.borlandlp.minigamesdtools.activepoints.type;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.activepoints.ActivePoint;
import com.gmail.borlandlp.minigamesdtools.activepoints.BuildSchema;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class StaticBlockPoint extends ActivePoint {
    private List<Location> usedBlocks = new ArrayList<>();
    private String direction;
    private int radius;
    private boolean hollow;
    private double health;

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public boolean isHollow() {
        return hollow;
    }

    public void setHollow(boolean hollow) {
        this.hollow = hollow;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    public void setUsedBlocks(List<Location> usedBlocks) {
        this.usedBlocks = usedBlocks;
    }

    public List<Location> getUsedBlocks() {
        return usedBlocks;
    }

    public void spawn() {
        Map<Location, Material> buildSchema = this.getBuildSchema().getSchema();
        for(Location location : buildSchema.keySet()) {
            location.getWorld().getBlockAt(location).setType(buildSchema.get(location));
            this.getActivePointController().getStaticPointsCache().add(location, this);
        }
        Debug.print(Debug.LEVEL.NOTICE, "spawn static point " + this.getName() + ". size:" + buildSchema.keySet().size());
        this.setUsedBlocks(new ArrayList<>(buildSchema.keySet()));
        this.setSpawned(true);
    }

    public void despawn() {
        List<Location> buildSchema = this.getUsedBlocks();
        for(Location location : buildSchema) {
            location.getWorld().getBlockAt(location).setType(Material.AIR);
            this.getActivePointController().getStaticPointsCache().remove(location);
        }
        Debug.print(Debug.LEVEL.NOTICE, "despawn static point " + this.getName() + ". size:" + this.getUsedBlocks().size());
        this.setSpawned(false);
    }

    public abstract BuildSchema getBuildSchema();
}
