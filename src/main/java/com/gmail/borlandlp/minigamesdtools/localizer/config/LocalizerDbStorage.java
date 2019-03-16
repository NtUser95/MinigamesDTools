package com.gmail.borlandlp.minigamesdtools.localizer.config;

import java.util.UUID;

public interface LocalizerDbStorage {
    String getLang(UUID uuid);
    void setLang(UUID uuid, String langCode);
}
