package com.gmail.borlandlp.minigamesdtools.gun.bullet;

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.Creator;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo;
import org.bukkit.World;

import java.util.Arrays;
import java.util.List;

@CreatorInfo(creatorId = "ghost_bullet")
public class GhostBulletCreator implements Creator {
    @Override
    public List<String> getDataProviderRequiredFields() {
        return Arrays.asList("world");
    }

    @Override
    public Object create(String ID, AbstractDataProvider dataProvider) throws Exception {
        return new GhostBullet((World) dataProvider.get("world"));
    }
}
