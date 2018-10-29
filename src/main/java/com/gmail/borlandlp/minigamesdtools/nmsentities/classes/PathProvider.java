package com.gmail.borlandlp.minigamesdtools.nmsentities.classes;

import net.minecraft.server.v1_12_R1.Vec3D;

public interface PathProvider {
    boolean pathEnding();
    void reloadPath();
    Vec3D getPath();
}
