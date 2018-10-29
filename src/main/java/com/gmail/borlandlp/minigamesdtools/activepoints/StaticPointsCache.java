package com.gmail.borlandlp.minigamesdtools.activepoints;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class StaticPointsCache {
    private Map<String, ActivePoint> pointBlocksCache = new HashMap<>();//(String)coordX+coordY+coordZ -> ActivePoint

    public void remove(Location location) {
        String xyz = location.getBlockX() + "" + location.getBlockY() + "" + location.getBlockZ();
        pointBlocksCache.remove(xyz);
    }

    public void add(Location location, ActivePoint activePoint) {
        String xyz = location.getBlockX() + "" + location.getBlockY() + "" + location.getBlockZ();
        pointBlocksCache.put(xyz, activePoint);
    }

    public ActivePoint get(Location location) {
        if(location == null) {
            return null;
        }

        return pointBlocksCache.get(location.getBlockX() + "" + location.getBlockY() + "" + location.getBlockZ());
    }

    public boolean contains(Location location) {
        String xyz = location.getBlockX() + "" + location.getBlockY() + "" + location.getBlockZ();
        return pointBlocksCache.containsKey(xyz);
    }
}
