package com.gmail.borlandlp.minigamesdtools.config;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;

import java.io.File;

public enum ConfigPath {
    MAIN(new File(MinigamesDTools.getInstance().getDataFolder(), "config.yml"), false, "main"),
    MESSAGES(new File(MinigamesDTools.getInstance().getDataFolder(),  "messages.yml"), false, "messages"),
    CONDITIONS(new File(MinigamesDTools.getInstance().getDataFolder(),  "conditions.yml"), false, "conditions"),
    CACHE_POINTS(new File(MinigamesDTools.getInstance().getDataFolder(), "cache" + File.separator + "points"), true, "cache_points"),
    TEAMS(new File(MinigamesDTools.getInstance().getDataFolder(),  "teams.yml"), false, "teams"),
    ARENA_FOLDER(new File(MinigamesDTools.getInstance().getDataFolder(), "arenas"), true, "arenas"),
    HOTBAR_SLOTS(new File(MinigamesDTools.getInstance().getDataFolder().getAbsoluteFile(), "hotbarslots.yml"), false, "hotbar_slots"),
    HOTBAR(new File(MinigamesDTools.getInstance().getDataFolder().getAbsoluteFile(), "hotbars.yml"), false, "hotbar"),
    ACTIVE_POINT(new File(MinigamesDTools.getInstance().getDataFolder().getAbsoluteFile(), "activepoints.yml"), false, "active_point"),
    ACTIVE_POINT_REACTIONS(new File(MinigamesDTools.getInstance().getDataFolder().getAbsoluteFile(), "activepointsreactions.yml"), false, "active_point_reactions"),
    ACTIVE_POINT_BEHAVIORS(new File(MinigamesDTools.getInstance().getDataFolder().getAbsoluteFile(), "activepointbehaviors.yml"), false, "active_point_behaviors"),
    SCENARIO_CHAIN(new File(MinigamesDTools.getInstance().getDataFolder().getAbsoluteFile(), "scenariochain.yml"), false, "scenario_chain"),
    SCENARIO(new File(MinigamesDTools.getInstance().getDataFolder().getAbsoluteFile(), "scenario.yml"), false, "scenario"),
    ARENA_LOBBY(new File(MinigamesDTools.getInstance().getDataFolder().getAbsoluteFile(), "arena_lobby.yml"), false, "arena_lobby"),
    SERVER_LOBBY(new File(MinigamesDTools.getInstance().getDataFolder().getAbsoluteFile(), "lobby.yml"), false, "server_lobby"),
    ADDONS(new File(MinigamesDTools.getInstance().getDataFolder().getAbsoluteFile(), "addons"), true, "addons"),
    INVENTORY_GUI(new File(MinigamesDTools.getInstance().getDataFolder().getAbsoluteFile(), "inventory_gui.yml"), false, "inventory_gui"),
    INVENTORY_GUI_SLOT(new File(MinigamesDTools.getInstance().getDataFolder().getAbsoluteFile(), "inventory_gui_slot.yml"), false, "inventory_gui_slot");

    private File path;
    private boolean isDir;
    private String typeId;

    ConfigPath(File p, boolean isDirectory, String typeId) {
        this.isDir = isDirectory;
        this.path = p;
        this.typeId = typeId;
    }

    public boolean isDir() {
        return isDir;
    }

    public File getPath() {
        return path;
    }

    public String getTypeId() {
        return typeId;
    }

    public static ConfigPath getByPoolId(String pool_id) {
        for (ConfigPath path : ConfigPath.values()) {
            if(path.getTypeId().equals(pool_id)) return path;
        }

        return null;
    }
}
