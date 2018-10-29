package com.gmail.borlandlp.minigamesdtools.nmsentities;

import com.gmail.borlandlp.minigamesdtools.APIComponent;
import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaComponent;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent;
import com.gmail.borlandlp.minigamesdtools.nmsentities.classes.PathProvider;
import com.gmail.borlandlp.minigamesdtools.nmsentities.entity.IMoveControllableEntity;
import com.gmail.borlandlp.minigamesdtools.util.NMSUtil;
import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.EntityInsentient;
import net.minecraft.server.v1_12_R1.Vec3D;

import java.util.*;

public class EntityController implements EntityAPI, APIComponent {
    private Map<IMoveControllableEntity, PathController> paths = new HashMap<>();
    private List<RegisteredEntity> registeredEntity = new ArrayList<>();

    @Override
    public List<PathController> getPaths() {
        return new ArrayList<>(paths.values());
    }

    @Override
    public void register(String name, int id, Class<? extends Entity> oldClass, Class<? extends Entity> newClass) {
        Debug.print(Debug.LEVEL.NOTICE, "register entity oldClass:" + oldClass + ", newClass:" + newClass);
        NMSUtil.registerEntity(name, id, oldClass, newClass);
        this.registeredEntity.add(new RegisteredEntity(name, id, oldClass, newClass));
    }

    @Override
    public void unregister(String name, int id, Class<? extends Entity> oldClass, Class<? extends Entity> newClass) {
        NMSUtil.unregisterEntity(name, id, oldClass, newClass);
        this.registeredEntity.removeIf(e -> e.getId() == id);
    }

    @Override
    public PathController addMovePaths(EntityInsentient entityInsentient, Vec3D[] paths, boolean isRepeating) throws Exception {
        if(!(entityInsentient instanceof IMoveControllableEntity)) {
           throw new Exception(entityInsentient.getName() + " isnt moveable entity!");
        } else if(this.isEntityMoveControlling(entityInsentient)) {
            throw new Exception(entityInsentient.getName() + " is already under moving control");
        }

        PathProvider[] providers = new PathProvider[paths.length];
        for(int i = 0; i < providers.length; i++) {
            providers[i] = ((IMoveControllableEntity) entityInsentient).vec2Path(paths[i]);
        }
        PathController pathController = new PathController((IMoveControllableEntity) entityInsentient, providers, isRepeating);
        this.paths.put((IMoveControllableEntity) entityInsentient, pathController);

        return pathController;
    }

    @Override
    public void removeMovePath(PathController pathController) {
        this.paths.remove(pathController.getEntity());
    }

    @Override
    public boolean isEntityMoveControlling(EntityInsentient entityInsentient) {
        return this.paths.containsKey(entityInsentient);
    }

    @Override
    public void setAttackTarget(EntityInsentient entityInsentient, EntityInsentient target) {

    }

    @Override
    public void setPersistentAttackTarget(EntityInsentient entityInsentient, EntityInsentient target) {

    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onUnload() {
        this.registeredEntity.parallelStream().forEach(e -> this.unregister(e.getName(), e.getId(), e.getOldClass(), e.getNewClass()));
    }

    public void update() {
        for (PathController pathController : this.getPaths()) {
            try {
                pathController.update();
            } catch (Exception e) {
                e.printStackTrace();
                this.removeMovePath(pathController);
            }

            if (pathController.isFinished()) {
                Debug.print(Debug.LEVEL.NOTICE, "removeMovePath");
                this.removeMovePath(pathController);
            }
        }
    }
}
