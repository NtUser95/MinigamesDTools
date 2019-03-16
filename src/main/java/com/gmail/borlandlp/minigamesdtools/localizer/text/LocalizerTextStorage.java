package com.gmail.borlandlp.minigamesdtools.localizer.text;

public interface LocalizerTextStorage {
    /**
     * Проверяет, содержит ли текст для textId
     * @param textId идентификатор текста
     * @return true/false
     */
    boolean containsText(String textId);

    /**
     * Возвращает текст для textId
     * @param textId
     * @return
     */
    String getText(String textId);

    String getLangCode();
}
