package com.gmail.borlandlp.minigamesdtools.creator;

import java.util.*;

public class RulesProvider {
    private List<String> rules = new ArrayList<>();

    public void addReqiredField(String key) {
        this.rules.add(key);
    }

    public void remove(String key) {
        this.rules.remove(key);
    }

    public boolean isCorrectProvider(AbstractDataProvider dataProvider) {
        for (String rulesKey : this.rules) {
            if(!dataProvider.contains(rulesKey)) {
                return false;
            }
        }

        return true;
    }
}
