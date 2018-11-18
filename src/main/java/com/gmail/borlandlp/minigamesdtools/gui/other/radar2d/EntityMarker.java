package com.gmail.borlandlp.minigamesdtools.gui.other.radar2d;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class EntityMarker implements Marker {
    private ChatColor chatColor;
    private Entity entity;

    public EntityMarker(Entity entity, ChatColor chatColor) {
        this.entity = entity;
        this.chatColor = chatColor;
    }

    @Override
    public boolean isOwner(Object object) {
        return object instanceof Entity && this.entity.equals((Entity) object);
    }

    @Override
    public ChatColor getColor() {
        return this.chatColor;
    }

    @Override
    public Location getLocation() {
        return this.entity.getLocation();
    }
}
