package com.gmail.borlandlp.minigamesdtools.creator;

import java.util.Hashtable;
import java.util.Map;

public class DataProvider implements AbstractDataProvider {
    private Map<String, Object> data = new Hashtable<>();

    @Override
    public boolean contains(String key) {
        return this.data.containsKey(key);
    }

    @Override
    public Object get(String key) {
        return this.data.get(key);
    }

    @Override
    public void set(String key, Object value) {
        this.data.put(key, value);
    }

    @Override
    public void remove(String key) {
        this.data.remove(key);
    }
}
