package com.gmail.borlandlp.minigamesdtools.localizer.text;

import com.gmail.borlandlp.minigamesdtools.Debug;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class YAMLTextStorage implements LocalizerTextStorage {
    private YamlConfiguration storage;
    private File file;
    private String langCode;

    public YAMLTextStorage(File file) {
        this.storage = YamlConfiguration.loadConfiguration(file);
        this.file = file;
    }

    @Override
    public boolean containsText(String textId) {
        if(textId.contains(".")) {
            String msg = "textId[ID:" + textId + "] contains a directory delimiter for YAML. This may cause a malfunction of the plugin.";
            Debug.print(Debug.LEVEL.WARNING, msg);
        }

        return this.storage.contains(textId);
    }

    @Override
    public String getText(String textId) {
        if(textId.contains(".")) {
            String msg = "textId[ID:" + textId + "] contains a directory delimiter for YAML. This may cause a malfunction of the plugin.";
            Debug.print(Debug.LEVEL.WARNING, msg);
        }

        return this.storage.getString(textId);
    }

    @Override
    public String getLangCode() {
        return this.langCode;
    }
}
