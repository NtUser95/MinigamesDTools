package com.gmail.borlandlp.minigamesdtools.gui.other.radar2d;

import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Radar2dHub implements ArenaPhaseComponent {
    private Map<Player, Radar> radar2dMap = new HashMap<>();

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

    }

    @Override
    public void gameEnded() {
        for (Player player : new ArrayList<>(this.radar2dMap.keySet())) {
            try {
                this.remove(player);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update() {
        for (Radar radar : this.radar2dMap.values()) {
            try {
                radar.draw();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void beforeRoundStarting() {

    }

    @Override
    public void onRoundEnd() {

    }
}
