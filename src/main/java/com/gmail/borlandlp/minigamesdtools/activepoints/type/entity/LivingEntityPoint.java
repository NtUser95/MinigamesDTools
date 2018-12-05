package com.gmail.borlandlp.minigamesdtools.activepoints.type.entity;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.activepoints.ActivePoint;
import com.gmail.borlandlp.minigamesdtools.nmsentities.PathController;
import com.gmail.borlandlp.minigamesdtools.nmsentities.entity.ISkybattleEntity;
import net.minecraft.server.v1_12_R1.EntityInsentient;
import net.minecraft.server.v1_12_R1.Vec3D;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public abstract class LivingEntityPoint extends ActivePoint {
    private Entity bukkitEntity;
    private EntityInsentient classTemplate;
    private Vec3D[] movePaths;
    private PathController pathController;

    public EntityInsentient getClassTemplate() {
        return classTemplate;
    }

    public Vec3D[] getMovePaths() {
        return movePaths;
    }

    public void setMovePaths(Vec3D[] movePaths) {
        this.movePaths = movePaths;
    }

    public void setClassTemplate(EntityInsentient classTemplate) {
        this.classTemplate = classTemplate;
    }

    public void spawn() {
        this.bukkitEntity = ((ISkybattleEntity)this.getClassTemplate()).spawn(this.getLocation());
        try {
            this.pathController = MinigamesDTools.getInstance().getEntityAPI().addMovePaths(this.getClassTemplate(), this.getMovePaths(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Debug.print(Debug.LEVEL.NOTICE, "spawn activepoint " + this.getName());

        this.setSpawned(true);
    }

    public void despawn() {
        MinigamesDTools.getInstance().getEntityAPI().removeMovePath(this.pathController);
        this.getBukkitEntity().remove();
        Debug.print(Debug.LEVEL.NOTICE, "despawn activepoint " + this.getName());

        this.setSpawned(false);
    }

    public Entity getBukkitEntity() {
        return bukkitEntity;
    }

    @Override
    public double getHealth() {
        return this.classTemplate.getHealth();
    }
}
