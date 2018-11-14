package com.gmail.borlandlp.minigamesdtools.gui.other.radar2d;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;

public class BlockMarker implements Marker {
    private ChatColor chatColor;
    private Block block;

    public BlockMarker(Block block, ChatColor color) {
        this.chatColor = color;
        this.block = block;
    }

    @Override
    public boolean isOwner(Object object) {
        return object instanceof Block && ((Block) object).getLocation().toString().equals(this.getLocation().toString());
    }

    @Override
    public ChatColor getColor() {
        return this.chatColor;
    }

    @Override
    public Location getLocation() {
        return this.block.getLocation();
    }
}
