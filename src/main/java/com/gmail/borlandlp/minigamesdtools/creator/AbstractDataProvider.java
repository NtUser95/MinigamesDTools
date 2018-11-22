package com.gmail.borlandlp.minigamesdtools.creator;

import java.util.Set;

public interface AbstractDataProvider {
    boolean contains(String key);
    Object get(String key);
    void set(String key, Object value);
    void remove(String key);
    Set<String> getKeys();
}
