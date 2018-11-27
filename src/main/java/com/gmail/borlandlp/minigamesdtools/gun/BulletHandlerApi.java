package com.gmail.borlandlp.minigamesdtools.gun;

import com.gmail.borlandlp.minigamesdtools.APIComponent;
import com.gmail.borlandlp.minigamesdtools.gun.bullet.GhostBullet;

public interface BulletHandlerApi extends APIComponent {
    void addBullet(GhostBullet bullet);
    void removeBullet(GhostBullet bullet);
}
