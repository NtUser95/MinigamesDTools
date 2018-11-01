package com.gmail.borlandlp.minigamesdtools.conditions;

import java.util.List;

public class PlayerCheckResult {
    private List<String> errId;
    private CheckResult result;

    public PlayerCheckResult(CheckResult r, List<String> eIds) {
        this.errId = eIds;
        this.result = r;
    }

    public List<String> getErrId() {
        return errId;
    }

    public CheckResult getResult() {
        return result;
    }

    public enum CheckResult {
        ALLOWED,
        DENIED
    }
}
