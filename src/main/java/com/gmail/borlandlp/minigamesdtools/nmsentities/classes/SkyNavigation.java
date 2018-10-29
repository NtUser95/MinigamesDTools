package com.gmail.borlandlp.minigamesdtools.nmsentities.classes;

import net.minecraft.server.v1_12_R1.*;

public class SkyNavigation extends Navigation {
    public SkyNavigation(EntityInsentient entityInsentient, World world) {
        super(entityInsentient, world);
    }

    /*
    ** Source from deobfuscated vanilla server
    public void onUpdateNavigation() {
      ++this.totalTicks;
      if (this.tryUpdatePath) {
         this.updatePath();
      }

      if (!this.noPath()) {
         Vec3d var1;
         if (this.canNavigate()) {
            this.pathFollow();
         } else if (this.currentPath != null && this.currentPath.getCurrentPathIndex() < this.currentPath.getCurrentPathLength()) {
            var1 = this.getEntityPosition();
            Vec3d var2 = this.currentPath.getVectorFromIndex(this.entity, this.currentPath.getCurrentPathIndex());
            if (var1.y > var2.y && !this.entity.onGround && MathHelper.floor(var1.x) == MathHelper.floor(var2.x) && MathHelper.floor(var1.z) == MathHelper.floor(var2.z)) {
               this.currentPath.setCurrentPathIndex(this.currentPath.getCurrentPathIndex() + 1);
            }
         }

         this.debugPathFinding();
         if (!this.noPath()) {
            var1 = this.currentPath.getPosition(this.entity);
            BlockPos var4 = (new BlockPos(var1)).down();
            AxisAlignedBB var3 = this.world.getBlockState(var4).getBoundingBox(this.world, var4);
            var1 = var1.subtract(0.0D, 1.0D - var3.maxY, 0.0D);
            this.entity.getMoveHelper().setMoveTo(var1.x, var1.y, var1.z, this.speed);
         }
      }
   }
    * */

    @Override
    public void d() {
        ++this.e;
        if (this.g) {
            this.k();
        }

        if (!this.o()) {
            Vec3D var1;
            if (this.b()) {
                this.n();
            } else if (this.c != null && this.c.e() < this.c.d()) {
                var1 = this.c();
                Vec3D var2 = this.c.a(this.a, this.c.e());
                if (var1.y > var2.y && !this.a.onGround && MathHelper.floor(var1.x) == MathHelper.floor(var2.x) && MathHelper.floor(var1.z) == MathHelper.floor(var2.z)) {
                    this.c.c(this.c.e() + 1);
                }
            }

            this.m();
            if (!this.o()) {
                var1 = this.c.a((Entity)this.a);
                BlockPosition var4 = (new BlockPosition(var1)).down();
                AxisAlignedBB var3 = this.b.getType(var4).e(this.b, var4);
                var1 = var1.a(0.0D, 1.0D - var3.e, 0.0D);
                this.a.getControllerMove().a(var1.x, var1.y, var1.z, this.d);
            }
        }
    }

    /*
    public boolean pathEnding() {
        //return this.currentPath != null && this.currentPath.isPathEnding();
        return this.c != null && this.c.b();
    }
    */
}
