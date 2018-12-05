package com.gmail.borlandlp.minigamesdtools.activepoints;

import com.gmail.borlandlp.minigamesdtools.APIComponent;
import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.activepoints.behaviors.Behavior;
import com.gmail.borlandlp.minigamesdtools.config.ConfigEntity;
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath;
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
        List<ConfigEntity> poolContents = MinigamesDTools.getInstance().getConfigProvider().getPoolContents(ConfigPath.ACTIVE_POINT);
        for(ConfigEntity configEntity : poolContents) {
            Debug.print(Debug.LEVEL.NOTICE,"[ActivePointController] load activePoint " + configEntity.getID());
            ActivePoint activePoint = null;
            try {
                activePoint = MinigamesDTools.getInstance().getActivePointsCreatorHub().createActivePoint(configEntity.getID(), new DataProvider());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(activePoint != null) {
                this.registerPoint(activePoint);
            } else {
                Debug.print(Debug.LEVEL.WARNING,"[ActivePointController] fail on load activePoint " + configEntity.getID());
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
    public void unregisterPoint(ActivePoint point) throws Exception {
        if(this.activatedPoints.contains(point)) {
            throw new Exception("The active point must be deactivated before calling this function.");
        }

        this.allPoints.remove(point);
        point.setActivePointController(null);
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
    public List<ActivePoint> getAllActivatedPoints() {
        return new ArrayList<>(this.activatedPoints);
    }

    @Override
    public List<ActivePoint> getAllPoints() {
        return new ArrayList<>(this.allPoints);
    }
}
