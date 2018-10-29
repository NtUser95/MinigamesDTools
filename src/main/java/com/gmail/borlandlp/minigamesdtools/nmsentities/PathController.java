package com.gmail.borlandlp.minigamesdtools.nmsentities;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.nmsentities.classes.PathProvider;
import com.gmail.borlandlp.minigamesdtools.nmsentities.entity.IMoveControllableEntity;

public class PathController {
    private PathProvider[] paths;
    private int curIndex = 0;
    private IMoveControllableEntity entity;
    private boolean isFinished;
    private boolean isRepeating;
    int failCounter;

    public PathController(IMoveControllableEntity entity, PathProvider[] paths, boolean isRepeating) {
        this.paths = paths;
        this.entity = entity;
        this.isRepeating = isRepeating;

        this.getEntity().setPath(this.getCurrentPath());
    }

    public PathProvider[] getPaths() {
        return this.paths;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public IMoveControllableEntity getEntity() {
        return entity;
    }

    public boolean isRepeating() {
        return isRepeating;
    }

    public PathProvider getCurrentPath() {
        return this.getPaths()[this.curIndex];
    }

    public void update() throws Exception {
        if(this.isFinished()) {
            return;
        } else if(this.curIndex >= this.getPaths().length) {
            throw new Exception("index out of bounds!");
        }

        if(this.getPaths()[this.curIndex].pathEnding()) {//if the entity has reached the path point
            //Debug.print(Debug.LEVEL.NOTICE, "next path");
            if((this.curIndex + 1) >= this.getPaths().length) {
                //Debug.print(Debug.LEVEL.NOTICE, "set finished");
                if(this.isRepeating()) {
                    this.curIndex = 0;
                } else {
                    this.isFinished = true;
                    return;
                }
            } else {
                this.curIndex++;
            }

            PathProvider nextPath = this.getCurrentPath();
            if(this.isRepeating() && nextPath.pathEnding()) {
                nextPath.reloadPath();
            }
            this.getEntity().setPath(nextPath);
            //Debug.print(Debug.LEVEL.NOTICE, "set path #" + this.curIndex);
        } else if(!this.getEntity().isFollowsThisPath(this.getCurrentPath())) { // if entity switched path
            //Debug.print(Debug.LEVEL.NOTICE, "inc failCounter #" + this.failCounter);
            if(++this.failCounter > 5) {
                Debug.print(Debug.LEVEL.NOTICE, "teleport entity");
                this.failCounter = 0;
                this.getEntity().teleport(this.getCurrentPath());
                this.getEntity().setPath(this.getCurrentPath());
            }
        }
    }
}
