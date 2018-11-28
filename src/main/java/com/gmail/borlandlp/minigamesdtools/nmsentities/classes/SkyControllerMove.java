package com.gmail.borlandlp.minigamesdtools.nmsentities.classes;


import net.minecraft.server.v1_12_R1.*;

public class SkyControllerMove extends net.minecraft.server.v1_12_R1.ControllerMove {
    public SkyControllerMove.Operation h;

    public SkyControllerMove(EntityInsentient entityInsentient) {
        super(entityInsentient);
    }

    public void a() {
        float var9;
        if (this.h == SkyControllerMove.Operation.STRAFE) {
            float var1 = (float)this.a.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue();
            float var2 = (float)this.e * var1;
            float var3 = this.f;
            float var4 = this.g;
            float var5 = MathHelper.c(var3 * var3 + var4 * var4);
            if (var5 < 1.0F) {
                var5 = 1.0F;
            }

            var5 = var2 / var5;
            var3 *= var5;
            var4 *= var5;
            float var6 = MathHelper.sin(this.a.yaw * 0.017453292F);
            float var7 = MathHelper.cos(this.a.yaw * 0.017453292F);
            float var8 = var3 * var7 - var4 * var6;
            var9 = var4 * var7 + var3 * var6;
            NavigationAbstract var10 = this.a.getNavigation();
            if (var10 != null) {
                PathfinderAbstract var11 = var10.r();
                if (var11 != null && var11.a(this.a.world, MathHelper.floor(this.a.locX + (double)var8), MathHelper.floor(this.a.locY), MathHelper.floor(this.a.locZ + (double)var9)) != PathType.WALKABLE) {
                    this.f = 1.0F;
                    this.g = 0.0F;
                    var2 = var1;
                }
            }

            this.a.k(var2);
            this.a.n(this.f);
            this.a.p(this.g);
            this.h = SkyControllerMove.Operation.WAIT;
        } else if (this.h == SkyControllerMove.Operation.MOVE_TO) {
            this.h = SkyControllerMove.Operation.WAIT;
            double var12 = this.b - this.a.locX;
            double var13 = this.d - this.a.locZ;
            double var14 = this.c - this.a.locY;
            double var15 = var12 * var12 + var14 * var14 + var13 * var13;
            if (var15 < 1D) {
                // stop walking here
                this.a.n(0.0F);
                return;
            }

            var9 = (float)(MathHelper.c(var13, var12) * 57.2957763671875D) - 90.0F;
            this.a.yaw = this.a(this.a.yaw, var9, 90.0F);
            this.a.k((float)(this.e * this.a.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue()));
            if (var14 > (double)this.a.P && var12 * var12 + var13 * var13 < (double)Math.max(1.0F, this.a.width)) {
                this.a.getControllerJump().a();
                this.h = SkyControllerMove.Operation.JUMPING;
            }
        } else if (this.h == SkyControllerMove.Operation.JUMPING) {
            this.a.k((float)(this.e * this.a.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue()));
            if (this.a.onGround) {
                this.h = SkyControllerMove.Operation.WAIT;
            }
        } else {
            this.a.n(0.0F);
        }
    }
}
