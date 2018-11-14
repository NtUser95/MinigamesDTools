package com.gmail.borlandlp.minigamesdtools.database;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

// взято откуда-то из интернетов и так и не трогал, и не использовал это, почти. переделать бы по нормальному.
public class Error {

    public static void execute(JavaPlugin plugin, Exception ex){
        plugin.getLogger().log(Level.SEVERE, "Couldn't execute MySQL statement: ", ex);
    }

    public static void close(JavaPlugin plugin, Exception ex){
        plugin.getLogger().log(Level.SEVERE, "Failed to close MySQL connection: ", ex);
    }

}
