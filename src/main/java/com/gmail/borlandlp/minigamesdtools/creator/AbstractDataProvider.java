package com.gmail.borlandlp.minigamesdtools.creator;

public interface AbstractDataProvider {
    boolean contains(String key);
    Object get(String key);
    void set(String key, Object value);
    void remove(String key);
}
