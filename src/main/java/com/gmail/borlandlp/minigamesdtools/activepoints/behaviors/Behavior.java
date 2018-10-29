package com.gmail.borlandlp.minigamesdtools.activepoints.behaviors;

import com.gmail.borlandlp.minigamesdtools.activepoints.ActivePoint;

public abstract class Behavior {
    private ActivePoint activePoint;
    private String name;
    private int delay;
    private int period;

    public int getPeriod() {
        return period;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public ActivePoint getActivePoint() {
        return activePoint;
    }

    public void setActivePoint(ActivePoint activePoint) {
        this.activePoint = activePoint;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void tick();
}
