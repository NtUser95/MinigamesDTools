package com.gmail.borlandlp.minigamesdtools.config;

import org.bukkit.configuration.ConfigurationSection;

public class ConfigEntity {
    private String ID;
    private ConfigurationSection data;

    public ConfigEntity(String ID, ConfigurationSection d) {
        this.ID = ID;
        this.data = d;
    }

    public String getID() {
        return ID;
    }

    public ConfigurationSection getData() {
        return data;
    }
}
