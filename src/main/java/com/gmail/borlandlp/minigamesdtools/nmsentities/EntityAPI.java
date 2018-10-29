package com.gmail.borlandlp.minigamesdtools.nmsentities;

import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.EntityInsentient;
import net.minecraft.server.v1_12_R1.Vec3D;

import java.util.List;

public interface EntityAPI {
    List<PathController> getPaths();
    PathController addMovePaths(EntityInsentient entityInsentient, Vec3D[] paths, boolean isRepeating) throws Exception;
    void removeMovePath(PathController pathController);
    boolean isEntityMoveControlling(EntityInsentient entityInsentient);
    void setAttackTarget(EntityInsentient entityInsentient, EntityInsentient target);
    void setPersistentAttackTarget(EntityInsentient entityInsentient, EntityInsentient target);
    void register(String name, int id, Class<? extends Entity> oldClass, Class<? extends Entity> newClass);
    void unregister(String name, int id, Class<? extends Entity> oldClass, Class<? extends Entity> newClass);
}
