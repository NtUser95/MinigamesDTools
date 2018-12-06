package com.gmail.borlandlp.minigamesdtools.gun;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.gun.bullet.GhostBullet;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class BulletHandler implements BulletHandlerApi {
    private List<GhostBullet> bullets = new ArrayList<>();
    private BukkitTask task;

    @Override
    public void addBullet(GhostBullet bullet) {
        this.bullets.add(bullet);
    }

    @Override
    public void removeBullet(GhostBullet bullet) {
        this.bullets.remove(bullet);
    }

    private List<GhostBullet> getBullets() {
        return new ArrayList<>(this.bullets);
    }

    @Override
    public void onLoad() {
        final BulletHandler task = this;
        this.task = new BukkitRunnable() {
            public void run() {
                task.update();

            }
        }.runTaskTimer(MinigamesDTools.getInstance(), 0, 1);
    }

    @Override
    public void onUnload() {
        if(this.task != null && !this.task.isCancelled()) {
            this.task.cancel();
        }

        for (GhostBullet bullet : this.bullets) {
            bullet.die();
        }
    }

    private void update() {
        for (GhostBullet bullet : new ArrayList<>(this.bullets)) {
            try {
                bullet.B_();// B_() -> update()
                if(!bullet.isAlive()) {
                    this.removeBullet(bullet);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
