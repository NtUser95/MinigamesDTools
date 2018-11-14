package com.gmail.borlandlp.minigamesdtools.gui.other.radar2d;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;

public interface Marker {
    boolean isOwner(Object object);
    ChatColor getColor();
    Location getLocation();
}
