package com.gmail.borlandlp.minigamesdtools.conditions;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ConditionsChain {
    private List<AbstractCondition> conditions;
    private String lastErrorID;

    public ConditionsChain(List<AbstractCondition> c) {
        this.conditions = c;
    }

    public PlayerCheckResult check(Player p) {
        List<String> errIds = new ArrayList<>();
        for (AbstractCondition abstractCondition : this.conditions) {
            try {
                if(!abstractCondition.isValidPlayer(p)) {
                    errIds.add(abstractCondition.getErrorId());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new PlayerCheckResult(errIds.size() > 0 ? PlayerCheckResult.CheckResult.DENIED : PlayerCheckResult.CheckResult.ALLOWED, errIds);
    }

    public String getLastErrorID() {
        return lastErrorID;
    }
}
