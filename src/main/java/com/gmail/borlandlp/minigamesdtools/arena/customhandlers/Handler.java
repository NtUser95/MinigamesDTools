package com.gmail.borlandlp.minigamesdtools.arena.customhandlers;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public abstract class Handler {
    private BukkitTask task;
    private int delay = 0;
    private int period = 20;

    void start() {
        final Handler task = this;
        this.task = new BukkitRunnable() {
            public void run() {
                task.doWork();
            }
        }.runTaskTimer(MinigamesDTools.getInstance(), this.delay, this.period);
    }

    void stop() {
        if(this.task != null) {
            this.task.cancel();
        }
    }

    public abstract void doWork();
}
