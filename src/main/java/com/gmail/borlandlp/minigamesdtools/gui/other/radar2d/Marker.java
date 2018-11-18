package com.gmail.borlandlp.minigamesdtools.gui.other.radar2d;

import org.bukkit.ChatColor;
import org.bukkit.Location;

public interface Marker {
    boolean isOwner(Object object);
    ChatColor getColor();
    Location getLocation();
}
