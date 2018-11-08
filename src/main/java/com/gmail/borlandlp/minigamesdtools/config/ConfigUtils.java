package com.gmail.borlandlp.minigamesdtools.config;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class ConfigUtils {
    public static void checkAndRestoreDefaultConfigs() {
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

    public static void restoreResource2Config(String configName) {
        try {
            Reader defConfigStream = new InputStreamReader(MinigamesDTools.getInstance().getResource(configName), StandardCharsets.UTF_8);
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            defConfig.save(new File(MinigamesDTools.getInstance().getDataFolder().getAbsoluteFile(), configName));
        } catch(Exception ex) {
            Debug.print(Debug.LEVEL.WARNING, "An error occurred while restoring file:'" + configName + "'");
            ex.printStackTrace();
        }
    }
}
