package com.gmail.borlandlp.minigamesdtools.nmsentities.entity;

import com.gmail.borlandlp.minigamesdtools.nmsentities.classes.PathProvider;
import net.minecraft.server.v1_12_R1.Vec3D;

public interface IMoveControllableEntity {
    void setPath(PathProvider path);
    PathProvider vec2Path(Vec3D vec3D);
    boolean isFollowsThisPath(PathProvider pathProvider);
    void teleport(PathProvider pathProvider);
}
