package com.gmail.borlandlp.minigamesdtools.gui.other.radar2d;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Radar2dHub implements ArenaPhaseComponent {
    private Map<Player, Radar> radar2dMap = new HashMap<>();
    private BukkitTask task;

    public Map<Player, Radar> getAll() {
        return new HashMap<>(this.radar2dMap);
    }

    public Radar get(Player p) {
        return this.radar2dMap.get(p);
    }

    public void add(Player p, Radar r) {
        try {
            r.onLoad();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        this.radar2dMap.put(p, r);
    }

    public void remove(Player p) {
        if(this.radar2dMap.containsKey(p)) {
            try {
                this.radar2dMap.get(p).onUnload();
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.radar2dMap.remove(p);
        }
    }

    @Override
    public void onInit() {

    }

    @Override
    public void beforeGameStarting() {
        final Radar2dHub obj = this;
        this.task = new BukkitRunnable() {
            public void run() {
                obj.drawRadars();
            }
        }.runTaskTimer(MinigamesDTools.getInstance(), 0, 5);
    }

    @Override
    public void gameEnded() {
        if(this.task != null) {
            try {
                this.task.cancel();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Player player : new ArrayList<>(this.radar2dMap.keySet())) {
            try {
                this.remove(player);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void drawRadars() {
        for (Radar radar : this.radar2dMap.values()) {
            try {
                radar.draw();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void beforeRoundStarting() {

    }

    @Override
    public void onRoundEnd() {

    }
}
