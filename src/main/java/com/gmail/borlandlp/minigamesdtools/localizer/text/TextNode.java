package com.gmail.borlandlp.minigamesdtools.localizer.text;

import java.util.Set;

/**
 * Содержит в себе представление какой-либо строки на различные языки.
 */
public interface TextNode {
    /**
     * @return Список доступных языков для этой ноды
     */
    Set<String> containsLanguages();

    /**
     * @param langCode - двухзначный код языка, для которого будет запрошена строка ноды.
     * @return String неформатированное представление ноды для langCode
     */
    String getForLanguage(String langCode);
}
