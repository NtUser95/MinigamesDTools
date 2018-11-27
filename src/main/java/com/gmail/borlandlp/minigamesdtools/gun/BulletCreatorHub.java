package com.gmail.borlandlp.minigamesdtools.gun;

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub;
import com.gmail.borlandlp.minigamesdtools.gun.bullet.GhostBullet;

public class BulletCreatorHub extends CreatorHub {
    public GhostBullet createBullet(String ID, AbstractDataProvider dataProvider) throws Exception {
        return (GhostBullet) this.create(ID, dataProvider);
    }
}
