package com.gmail.borlandlp.minigamesdtools.config;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

@Deprecated
public class ConfigManager {
    public void checkConfigs() {
        // restore plugin folder
        if(!MinigamesDTools.getInstance().getDataFolder().exists()) {
            MinigamesDTools.getInstance().getDataFolder().mkdirs();
        }

        for (ConfigPath config : ConfigPath.values()) {
            if(!config.getPath().exists()) {
                if(config.isDir()) {
                    config.getPath().mkdirs();
                } else {
                    restoreResource2Config(config.getPath().getAbsoluteFile().getName());
                }
            }
        }
    }

    public YamlConfiguration getConfig(ConfigPath conf) throws Exception {
        if(conf.isDir()) {
            throw new Exception("Directory cant be used for load config!");
        }

        return YamlConfiguration.loadConfiguration(conf.getPath());
    }

    public ConfigurationSection getConfigSection(ConfigPath conf, String conf_section) throws Exception {
        return this.getConfig(conf).getConfigurationSection(conf_section);
    }

    private void restoreResource2Config(String configName) {
        try {
            Reader defConfigStream = new InputStreamReader(MinigamesDTools.getInstance().getResource(configName), StandardCharsets.UTF_8);
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            defConfig.save(new File(MinigamesDTools.getInstance().getDataFolder().getAbsoluteFile(), configName));
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<String> getEnabledServerLobbyIds() {
        List<String> strs = new ArrayList<>();
        try {
            Configuration conf = this.getConfig(ConfigPath.SERVER_LOBBY);
            for (String lobbyID : conf.getKeys(false)) {
                if(conf.contains(lobbyID + ".enabled") && Boolean.parseBoolean(conf.get(lobbyID + ".enabled").toString())) {
                    strs.add(lobbyID);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return strs;
    }

    public Set<String> getActivepointsList() {
        Set<String> activePoints = new HashSet<>();
        try {
            activePoints = this.getConfig(ConfigPath.ACTIVE_POINT).getKeys(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return activePoints;
    }

    public File getAddonsPath() {
        return ConfigPath.ADDONS.getPath();
    }

    public File getArenasPath()
    {
        return ConfigPath.ARENA_FOLDER.getPath();
    }

   public YamlConfiguration getArenaConfig(String arenaName) {
       String path = getArenasPath() + File.separator + arenaName + ".yml";
       File file = new File(path);

       return YamlConfiguration.loadConfiguration(file);
   }

    public void setArenaState(String arenaName, boolean state) {
        File file = new File(MinigamesDTools.getInstance().getDataFolder() + File.separator + "arena" + File.separator + arenaName + ".yml");
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        yaml.set("enabled", false);
        try {
            yaml.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   public List<String> getArenasName() {
       List<String> names = new ArrayList<>();
       for(String name : this.getArenasPath().list()) {
          names.add(name.replace(".yml", ""));
       }

       return names;
   }

    public enum ConfigPath {
        MAIN(new File(MinigamesDTools.getInstance().getDataFolder(), "config.yml"), false),
        MESSAGES(new File(MinigamesDTools.getInstance().getDataFolder(),  "messages.yml"), false),
        CONDITIONS(new File(MinigamesDTools.getInstance().getDataFolder(),  "conditions.yml"), false),
        CACHE_POINTS(new File(MinigamesDTools.getInstance().getDataFolder(), "cache" + File.separator + "points"), true),
        TEAMS(new File(MinigamesDTools.getInstance().getDataFolder(),  "teams.yml"), false),
        ARENA_FOLDER(new File(MinigamesDTools.getInstance().getDataFolder(), "arenas"), true),
        HOTBAR_SLOTS(new File(MinigamesDTools.getInstance().getDataFolder().getAbsoluteFile(), "hotbarslots.yml"), false),
        HOTBAR(new File(MinigamesDTools.getInstance().getDataFolder().getAbsoluteFile(), "hotbars.yml"), false),
        ACTIVE_POINT(new File(MinigamesDTools.getInstance().getDataFolder().getAbsoluteFile(), "activepoints.yml"), false),
        ACTIVE_POINT_REACTIONS(new File(MinigamesDTools.getInstance().getDataFolder().getAbsoluteFile(), "activepointsreactions.yml"), false),
        ACTIVE_POINT_BEHAVIORS(new File(MinigamesDTools.getInstance().getDataFolder().getAbsoluteFile(), "activepointbehaviors.yml"), false),
        SCENARIO_CHAIN(new File(MinigamesDTools.getInstance().getDataFolder().getAbsoluteFile(), "scenariochain.yml"), false),
        SCENARIO(new File(MinigamesDTools.getInstance().getDataFolder().getAbsoluteFile(), "scenario.yml"), false),
        RESPAWN_LOBBY(new File(MinigamesDTools.getInstance().getDataFolder().getAbsoluteFile(), "respawnlobby.yml"), false),
        SPECTATOR_LOBBY(new File(MinigamesDTools.getInstance().getDataFolder().getAbsoluteFile(), "spectatorlobby.yml"), false),
        STARTER_LOBBY(new File(MinigamesDTools.getInstance().getDataFolder().getAbsoluteFile(), "starterlobby.yml"), false),
        SERVER_LOBBY(new File(MinigamesDTools.getInstance().getDataFolder().getAbsoluteFile(), "lobby.yml"), false),
        ADDONS(new File(MinigamesDTools.getInstance().getDataFolder().getAbsoluteFile(), "addons"), true),
        INVENTORY_GUI(new File(MinigamesDTools.getInstance().getDataFolder().getAbsoluteFile(), "inventory_gui.yml"), false),
        INVENTORY_GUI_SLOT(new File(MinigamesDTools.getInstance().getDataFolder().getAbsoluteFile(), "inventory_gui_slot.yml"), false);

        private File path;
        private boolean isDir;

        ConfigPath(File p, boolean isDirectory) {
            this.isDir = isDirectory;
            this.path = p;
        }

        public boolean isDir() {
            return isDir;
        }

        public File getPath() {
            return path;
        }
    }
}
