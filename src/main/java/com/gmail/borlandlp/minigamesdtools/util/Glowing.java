package com.gmail.borlandlp.minigamesdtools.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.inventivetalent.glow.GlowAPI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Glowing {
    public static void addGlowEffect(Entity entity, String color) {
        if (entity != null) {
            GlowAPI.Color glowColor = GlowAPI.Color.valueOf(color);
            Collection<Player> players = new ArrayList<>(Bukkit.getServer().getOnlinePlayers());
            GlowAPI.setGlowing(entity, glowColor, players);
        }
    }

    public static void removeGlowEffect(Entity entity) {
        if (entity != null) {
            Collection<Player> players = new ArrayList<>(Bukkit.getServer().getOnlinePlayers());
            GlowAPI.setGlowing(entity, null, players);
        }
    }

    public static List<String> getAvailableColors() {
        List<String> colors = new ArrayList<>();
        for(GlowAPI.Color color : GlowAPI.Color.values()) {
            colors.add(color.name());
        }

        return colors;
    }
}
