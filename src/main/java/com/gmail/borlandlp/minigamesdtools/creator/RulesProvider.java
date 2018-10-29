package com.gmail.borlandlp.minigamesdtools.creator;

import java.util.*;

public class RulesProvider {
    private Map<String, Object> rules = new Hashtable<>();
    private Map<String, Class> keyType = new Hashtable<>();
    private Map<String, Class> valueType = new Hashtable<>();

    public void add(String key, Object data) {
        this.rules.put(key, data);
    }

    public void addCollection(String key, Object data, Class collectionType) {
        this.rules.put(key, data);
        this.valueType.put(key, collectionType);
    }

    public void addMap(String key, Object data, Class keyType, Class collectionType) {
        this.rules.put(key, data);
        this.keyType.put(key, keyType);
        this.valueType.put(key, collectionType);
    }

    public void remove(String key) {
        this.rules.remove(key);
    }

    private boolean isCollection(Object object) {
        return object.getClass().isAssignableFrom(Collection.class);
    }

    private boolean isMap(Object object) {
        return object.getClass().isAssignableFrom(Map.class);
    }

    private boolean isPrimitive(Object object) {
        return object.getClass().isAssignableFrom(Float.class)
                || object.getClass().isAssignableFrom(Byte.class)
                || object.getClass().isAssignableFrom(Short.class)
                || object.getClass().isAssignableFrom(Integer.class)
                || object.getClass().isAssignableFrom(Long.class)
                || object.getClass().isAssignableFrom(Double.class)
                || object.getClass().isAssignableFrom(Character.class)
                || object.getClass().isAssignableFrom(Boolean.class);
    }

    public boolean isCorrectProvider(AbstractDataProvider dataProvider) {
        for (String rulesKey : this.rules.keySet()) {
            if(!dataProvider.contains(rulesKey)) {
                return false;
            }

            Object rulesData = this.rules.get(rulesKey);
            Object providerData = dataProvider.get(rulesKey);

            if(isCollection(rulesData) && !isCollection(providerData)) {
                return false;
            } else if(isCollection(rulesData) && isCollection(providerData)) {

                if(!providerData.getClass().isAssignableFrom(this.valueType.get(rulesKey))) {
                    return false;
                }
            }

            if(isMap(rulesData) && !isMap(providerData)) {
                return false;
            } else if(isMap(rulesData) && isMap(providerData)) {

            }

            if(isPrimitive(rulesData) && !isPrimitive(providerData)) {
                return false;
            }
        }

        return true;
    }
}
