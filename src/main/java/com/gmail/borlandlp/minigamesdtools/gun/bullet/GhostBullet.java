package com.gmail.borlandlp.minigamesdtools.gun.bullet;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;

import java.util.Iterator;
import java.util.List;

public class GhostBullet extends EntityDragonFireball {
    private int inc = 0;
    public GhostBullet(World world) {
        super(((CraftWorld) world).getHandle());
    }

    // onImpact
    protected void a(MovingObjectPosition var1) {
        Debug.print(Debug.LEVEL.NOTICE, "GhostBullet explode");
        MinigamesDTools.getInstance().getBulletHandlerApi().removeBullet(this);
        if (var1.entity == null || !var1.entity.s(this.shooter)) {
            if (!this.world.isClientSide) {
                List var2 = this.world.a(EntityLiving.class, this.getBoundingBox().grow(4.0D, 2.0D, 4.0D));
                if (!var2.isEmpty()) {
                    Iterator var4 = var2.iterator();
                    while(var4.hasNext()) {
                        EntityLiving var5 = (EntityLiving)var4.next();
                        CraftEntity craftEntity = var5.getBukkitEntity();
                        ((LivingEntity) craftEntity).damage(5D);
                    }
                }
                this.die();
            }

        }
    }

    public void B_() {
        super.B_();
        if(this.inc++ >= 20) {
            System.out.println(this.locX + "/" + this.locY + "/" + this.locZ + "#" + this.motX + "/" + this.motY + "/" + this.motY);
            this.world.addParticle(EnumParticle.CLOUD, this.locX, this.locY, this.locZ, 1D, 1D, 1D);
            this.inc = 0;
        }
    }
}
