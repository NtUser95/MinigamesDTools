package com.gmail.borlandlp.minigamesdtools.config;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.config.exception.DuplicateConfigException;
import com.gmail.borlandlp.minigamesdtools.config.exception.InvalidPathException;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConfigLoader {
    private List<File> paths = new ArrayList<>();

    public ConfigLoader() {}

    public void addPath(File path2Load) throws InvalidPathException {
        if(path2Load == null) {
            throw new InvalidPathException("Path cant be null!");
        } else if(!path2Load.isDirectory()) {
            throw new InvalidPathException("Path must be a directory!");
        }

        this.paths.add(path2Load);
    }

    /*
    * Подгружаем из каталогов конфигурационные файлы и регистрируем секции из них в их пулах.
    * пул - хранилище, имеющее свой псевдоним(пример: @hotbars) и содержащее в себе List<ConfigEntity>.
    * */
    public ConfigProvider load() throws InvalidPathException {
        if(this.paths.size() == 0) {
            Debug.print(Debug.LEVEL.WARNING, "Loader has no directories.");
        }

        ConfigProvider configProvider = new ConcreteConfigProvider();

        for (File path : this.paths) {
            for (String fileName : Objects.requireNonNull(path.list())) {
                if(!fileName.contains(".yml")) {
                    continue;
                }

                YamlConfiguration yaml = YamlConfiguration.loadConfiguration(new File(path, fileName));
                if(!yaml.contains("meta_info")) {
                    Debug.print(Debug.LEVEL.NOTICE, fileName + " doesn't contain meta_info - ignore file.");
                    continue;
                } else {
                    Debug.print(Debug.LEVEL.NOTICE,  "Load file: " + fileName);
                }

                ConfigPath poolId = ConfigPath.getByPoolId(yaml.get("meta_info.pool_id").toString());

                for (String keyID : yaml.getConfigurationSection("data").getKeys(false)) {
                    ConfigurationSection data = yaml.getConfigurationSection("data." + keyID);
                    ConfigEntity configEntity = new ConfigEntity(keyID, data);
                    try {
                        configProvider.registerEntity(poolId, configEntity);
                    } catch (DuplicateConfigException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return configProvider;
    }
}
