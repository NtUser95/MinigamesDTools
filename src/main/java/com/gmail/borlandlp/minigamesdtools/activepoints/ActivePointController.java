package com.gmail.borlandlp.minigamesdtools.activepoints;

import com.gmail.borlandlp.minigamesdtools.APIComponent;
import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.activepoints.behaviors.Behavior;
import com.gmail.borlandlp.minigamesdtools.creator.DataProvider;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class ActivePointController implements APIComponent, ActivePointsAPI {
    private List<ActivePoint> activatedPoints = new ArrayList<>();
    private List<ActivePoint> allPoints = new ArrayList<>();
    private StaticPointsCache staticPointsCache = new StaticPointsCache();
    private Listener listener;
    private BukkitTask task;

    @Override
    public void onLoad() {
        this.listener = new ActivePointsListener(this);
        MinigamesDTools.getInstance().getServer().getPluginManager().registerEvents(this.listener, MinigamesDTools.getInstance());

        final ActivePointController task = this;
        this.task = new BukkitRunnable() {
            public void run() {
                task.update();
            }
        }.runTaskTimer(MinigamesDTools.getInstance(), 0, 20);

        // load default activepoints
        for(String activepointID : MinigamesDTools.getInstance().getConfigManager().getActivepointsList()) {
            Debug.print(Debug.LEVEL.NOTICE,"[ActivePointController] load activePoint " + activepointID);
            ActivePoint activePoint = null;
            try {
                activePoint = MinigamesDTools.getInstance().getActivePointsCreatorHub().createActivePoint(activepointID, new DataProvider());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(activePoint != null) {
                this.registerPoint(activePoint);
            } else {
                Debug.print(Debug.LEVEL.WARNING,"[ActivePointController] fail on load activePoint " + activepointID);
            }
        }
    }

    @Override
    public void onUnload() {
        this.task.cancel();
        HandlerList.unregisterAll(this.listener);
    }

    public ActivePoint searchPointByID(String ID) {
        for (ActivePoint activePoint : this.getAllPoints()) {
            if(activePoint.getName().equals(ID)) return activePoint;
        }

        return null;
    }

    @Override
    public void registerPoint(ActivePoint point) {
        point.setActivePointController(this);
        this.allPoints.add(point);
        Debug.print(Debug.LEVEL.NOTICE,"[ActivePointController] register activePoint: " + point.getName() + " for controller.");
    }

    @Override
    public void unregisterPoint(ActivePoint point) {
        this.allPoints.remove(point);
        Debug.print(Debug.LEVEL.NOTICE,"[ActivePointController] unregister activePoint: " + point.getName() + " for controller.");
    }

    @Override
    public void activatePoint(ActivePoint point) {
        point.spawn();
        activatedPoints.add(point);
    }

    @Override
    public void deactivatePoint(ActivePoint point) {
        if(point != null && activatedPoints.contains(point)) {
            point.despawn();
            activatedPoints.remove(point);
        }
    }

    private void update() {
        for (ActivePoint activePoint : this.activatedPoints) {
            for (Behavior behavior : activePoint.getBehaviors()) {
                behavior.tick();
            }
        }
    }

    @Override
    public StaticPointsCache getStaticPointsCache() {
        return staticPointsCache;
    }

    @Override
    public List<ActivePoint> getAllPoints() {
        return new ArrayList<>(this.allPoints);
    }
}
