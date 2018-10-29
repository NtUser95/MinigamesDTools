package com.gmail.borlandlp.minigamesdtools.activepoints;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.activepoints.behaviors.Behavior;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

// unused & deprecated class ?
public class ScheduleTaskWrapper {
    private BukkitTask task;
    private Behavior behavior;
    private int delay;
    private int period;

    public ScheduleTaskWrapper(Behavior behavior, int delay, int period) {
        this.delay = delay;
        this.period = period;
        this.behavior = behavior;
    }

    public int getDelay() {
        return delay;
    }

    public int getPeriod() {
        return period;
    }

    private Behavior getBehavior() {
        return behavior;
    }

    private BukkitTask getTask() {
        return this.task;
    }

    public void start() {
        final Behavior behavior = this.getBehavior();
        task = new BukkitRunnable() {
            public void run() {
                behavior.tick();
            }
        }.runTaskTimer(MinigamesDTools.getInstance(), this.getDelay(), this.getPeriod());
    }

    public void stop() {
        if(this.getTask() != null && !this.getTask().isCancelled()) this.getTask().cancel();
    }
}
