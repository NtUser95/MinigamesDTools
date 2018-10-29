package com.gmail.borlandlp.minigamesdtools.nmsentities.classes;

import net.minecraft.server.v1_12_R1.PathEntity;
import net.minecraft.server.v1_12_R1.PathPoint;
import net.minecraft.server.v1_12_R1.Vec3D;

/*
* Spigot: net.minecraft.server.v1_12_R1.PathEntity
* Vanilla: net.minecraft.pathfinding.Path
* */

public class SkyPathEntity extends PathEntity implements PathProvider {
    public SkyPathEntity(PathPoint[] pathPoints) {
        super(pathPoints);
    }

    @Override
    public boolean pathEnding() {
        return this.b();
    }

    @Override
    public void reloadPath() {
        this.c(0);
    }

    @Override
    public Vec3D getPath() {
        return this.f();
    }
}
