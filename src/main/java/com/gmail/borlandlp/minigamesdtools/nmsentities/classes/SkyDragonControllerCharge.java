package com.gmail.borlandlp.minigamesdtools.nmsentities.classes;

import net.minecraft.server.v1_12_R1.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;

/*
 * Spigot: net.minecraft.server.v1_12_R1.DragonControllerCharge
 * Vanilla: net.minecraft.entity.boss.dragon.phase.PhaseChargingPlayer
 * */

public class SkyDragonControllerCharge extends AbstractDragonController implements PathProvider {
    private static final Logger b = LogManager.getLogger();
    private Vec3D c;
    private int d;
    private boolean pathEnding = false;

    public SkyDragonControllerCharge(EntityEnderDragon entityEnderDragon) {
        super(entityEnderDragon);
    }

    public boolean pathEnding() {
        return pathEnding;
    }

    @Override
    public void reloadPath() {
        this.pathEnding = false;
    }

    @Override
    public Vec3D getPath() {
        return this.g();
    }

    @Override
    public void c() {
        if (this.g() == null) {
            b.warn("Aborting charge player as no target was set.");
            //this.a.getDragonControllerManager().setControllerPhase(DragonControllerPhase.a);
            this.pathEnding = true;
        } else if (this.d > 0 && this.d++ >= 10) {
            //this.a.getDragonControllerManager().setControllerPhase(DragonControllerPhase.a);
            this.pathEnding = true;
        } else {
            double var1 = this.c.c(this.a.locX, this.a.locY, this.a.locZ);
            if (var1 < 100.0D || var1 > 22500.0D || this.a.positionChanged || this.a.B) {
                ++this.d;
            }
        }
    }

    public void d() {
        this.c = null;
        this.d = 0;
    }

    public void a(Vec3D var1) {
        this.c = var1;
    }

    public float f() {
        return 3.0F;
    }

    @Nullable
    public Vec3D g() {
        return this.c;
    }

    public DragonControllerPhase<DragonControllerCharge> getControllerPhase() {
        return DragonControllerPhase.i;
    }
}
