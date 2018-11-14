package com.gmail.borlandlp.minigamesdtools.gui.other.radar2d;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface Radar {
    void draw();
    int getDrawDistance();
    void setDrawDistance(int drawDistance);
    Player getViewer();
    void setViewer(Player player);
    void addMarker(Marker marker);
    void removeMarker(Marker marker);
    Marker getCurrentMarker(Block block);
    Marker getCurrentMarker(Entity entity);
    void onUnload();
    void onLoad();
}
