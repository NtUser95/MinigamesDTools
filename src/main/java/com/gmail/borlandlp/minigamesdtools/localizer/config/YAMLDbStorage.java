package com.gmail.borlandlp.minigamesdtools.localizer.config;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class YAMLDbStorage implements LocalizerDbStorage {
    private File file;
    private YamlConfiguration storage;

    public YAMLDbStorage(File storageFile) {
        this.file = storageFile;
        this.storage = YamlConfiguration.loadConfiguration(storageFile);
    }

    @Override
    public String getLang(UUID uuid) {
        return storage.get(uuid.toString()).toString();
    }

    @Override
    public void setLang(UUID uuid, String langCode) {
        storage.set(uuid.toString(), langCode);
        try {
            storage.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
