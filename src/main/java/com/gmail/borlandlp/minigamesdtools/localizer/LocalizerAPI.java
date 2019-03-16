package com.gmail.borlandlp.minigamesdtools.localizer;

import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Компонент, хранящий языковые настройки игрока, а также отвечающий за хранение локализованного текста.
 */
public interface LocalizerAPI {
    /**
     * Устанавливает лвухзначное представление языка.
     * @param uuid
     */
    void setLanguage(UUID uuid, String langCode);

    /**
     * @param uuid
     * @return Двузначное представление языка. en, ru, de
     */
    String getLanguage(UUID uuid);

    String getLocalizedText(UUID uuid, String textId);
    String getLocalizedText(String langCode, String textId);
}
