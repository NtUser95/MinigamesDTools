package com.gmail.borlandlp.minigamesdtools.gun.bullet;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.events.BlockDamageByEntityEvent;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;

import java.util.Iterator;
import java.util.List;

public class GhostBullet extends EntityDragonFireball {
    private int inc = 0;
    private World bukkitWorld;
    private double prevMotX;
    private double prevMotY;
    private double prevMotZ;
    public int maxLivingTicks = 100;
    public int livedTicks;

    public GhostBullet(World world) {
        super(((CraftWorld) world).getHandle());
        this.bukkitWorld = world;
    }

    // onImpact
    protected void a(MovingObjectPosition position) {
        Debug.print(Debug.LEVEL.NOTICE, "GhostBullet explode");
        MinigamesDTools.getInstance().getBulletHandlerApi().removeBullet(this);
        if (position.entity == null || !position.entity.s(this.shooter)) {
            if (!this.world.isClientSide) {
                List var2 = this.world.a(EntityLiving.class, this.getBoundingBox().grow(1.0D, 1.0D, 1.0D));
                if (!var2.isEmpty()) {
                    Iterator var4 = var2.iterator();
                    while(var4.hasNext()) {
                        EntityLiving var5 = (EntityLiving)var4.next();
                        CraftEntity craftEntity = var5.getBukkitEntity();
                        ((LivingEntity) craftEntity).damage(5D);
                    }
                }

                if (position.type == MovingObjectPosition.EnumMovingObjectType.BLOCK) {
                    BlockPosition blockposition = position.a();
                    Block hitBlock = this.bukkitWorld.getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
                    LivingEntity livingEntity = ((LivingEntity) this.shooter.getBukkitEntity());
                    Bukkit.getPluginManager().callEvent(new BlockDamageByEntityEvent(livingEntity, hitBlock, null, true));
                }

                this.die();
            }
        }
    }

    public void B_() {
        this.prevMotX = this.motX;
        this.prevMotY = this.motY;
        this.prevMotZ = this.motZ;
        super.B_();
        this.motX = this.prevMotX;
        this.motY = this.prevMotY;
        this.motZ = this.prevMotZ;

        if(this.inc++ >= 1) {
            this.bukkitWorld.spawnParticle(Particle.FIREWORKS_SPARK, new Location(this.bukkitWorld, this.locX, this.locY, this.locZ), 0, 0, 0, 0);
            this.inc = 0;
        }

        if(++this.livedTicks > this.maxLivingTicks) {
            this.die();
        }
    }

    @Override
    public void die() {
        super.die();
    }
}
