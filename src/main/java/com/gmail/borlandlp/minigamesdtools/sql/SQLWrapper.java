package com.gmail.borlandlp.minigamesdtools.sql;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

// взято откуда-то из интернетов и так и не трогал, и не использовал это, почти. переделать бы по нормальному.
public class SQLWrapper {

    private JavaPlugin plugin;

    public SQLWrapper(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public double getRatingOf(Player player) {
        return 0.0F;
    }

    public void addRating(Player player, double rating) {

    }

}
