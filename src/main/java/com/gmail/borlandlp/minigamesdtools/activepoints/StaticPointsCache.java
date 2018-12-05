package com.gmail.borlandlp.minigamesdtools.activepoints;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class StaticPointsCache {
    private Map<String, ActivePoint> pointBlocksCache = new HashMap<>();//(String) worldName|coordX|coordY|coordZ -> ActivePoint

    public void remove(Location location) {
        pointBlocksCache.remove(location2StringF(location));
    }

    public void add(Location location, ActivePoint activePoint) {
        pointBlocksCache.put(location2StringF(location), activePoint);
    }

    public ActivePoint get(Location location) {
        if(location == null) {
            return null;
        }

        return pointBlocksCache.get(location2StringF(location));
    }

    public boolean contains(Location location) {
        return pointBlocksCache.containsKey(location2StringF(location));
    }

    private String location2StringF(Location location) {
        return location.getWorld().getName() + "|" +
                location.getBlockX() + "|" +
                location.getBlockY() + "|" +
                location.getBlockZ();
    }
}
