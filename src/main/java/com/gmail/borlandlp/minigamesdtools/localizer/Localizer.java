package com.gmail.borlandlp.minigamesdtools.localizer;

import com.gmail.borlandlp.minigamesdtools.APIComponent;
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.localizer.config.LocalizerDbStorage;
import com.gmail.borlandlp.minigamesdtools.localizer.config.YAMLDbStorage;

import java.io.File;
import java.util.UUID;

public class Localizer implements APIComponent, LocalizerAPI {
    private String defaultLang = "en";
    private LocalizerDbStorage dbStorage;

    @Override
    public void onLoad() {
        File locPath = new File(MinigamesDTools.getInstance().getDataFolder().getAbsoluteFile(), "localizer");
        this.dbStorage = new YAMLDbStorage(new File(locPath, "db.yml"));
        File locConfig = new File(locPath, "config.yml");
        // load files from disk
        // load users config from disk
    }

    @Override
    public void onUnload() {
        // unload files from disk
    }

    @Override
    public void setLanguage(UUID uuid, String langCode) {
        this.dbStorage.setLang(uuid, langCode);
    }

    @Override
    public String getLanguage(UUID uuid) {
        return this.dbStorage.getLang(uuid);
    }

    @Override
    public String getLocalizedText(UUID uuid, String textId) {
        String langCode = this.dbStorage.getLang(uuid);

        return langCode != null ? this.getLocalizedText(langCode, textId)
                : this.getLocalizedText(this.defaultLang, textId);
    }

    @Override
    public String getLocalizedText(String langCode, String textId) {
        return null;
    }
}
