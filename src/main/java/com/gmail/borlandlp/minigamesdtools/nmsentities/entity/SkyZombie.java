package com.gmail.borlandlp.minigamesdtools.nmsentities.entity;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.nmsentities.classes.PathProvider;
import com.gmail.borlandlp.minigamesdtools.nmsentities.classes.SkyPathEntity;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftZombie;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;

/*
* Spigot: net.minecraft.server.v1_12_R1.EntityZombie
* Vanilla: net.minecraft.entity.monster.EntityZombie
* */

public class SkyZombie extends EntityZombie implements IMoveControllableEntity, IControllable, ISkybattleEntity {
    private CraftZombie bukkitEntity;
    private boolean isUsedMinecraftAI = true;

    public SkyZombie(org.bukkit.World world) {
        super((World) ((CraftWorld) world).getHandle());
        this.bukkitEntity = new CraftZombie((CraftServer) Bukkit.getServer(), this);
    }

    public SkyZombie(org.bukkit.World world, boolean isUsedMinecraftAI) {
        super((World) ((CraftWorld) world).getHandle());
        this.bukkitEntity = new CraftZombie((CraftServer) Bukkit.getServer(), this);
        this.isUsedMinecraftAI = isUsedMinecraftAI;
    }

    /*
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIZombieAttack(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }
    */
    @Override
    protected void r() {
        if(this.isUsedMinecraftAI()) {
            this.goalSelector.a(0, new PathfinderGoalFloat(this));
            this.goalSelector.a(2, new PathfinderGoalZombieAttack(this, 1.0D, false));
            this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
            this.goalSelector.a(7, new PathfinderGoalRandomStrollLand(this, 1.0D));
            this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
            this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
        }

        this.do_();
    }

    public boolean isUsedMinecraftAI() {
        return isUsedMinecraftAI;
    }

    @Override
    public CraftZombie getBukkitEntity()
    {
        return bukkitEntity;
    }

    protected void initAttributes()
    {
        super.initAttributes();
    }

    public Zombie spawn(Location loc)  {
        this.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        this.world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);

        return getBukkitEntity();
    }

    @Override
    public void setPath(PathProvider path) {
        if(path != null) {// bg -> moveForward speed
            this.getNavigation().a((PathEntity) path, 2);
        }
    }

    @Override
    public PathProvider vec2Path(Vec3D vec3D) {
        if(this.getNavigation() == null) {
            Debug.print(Debug.LEVEL.NOTICE, "navigation of SkyZombie is null!");
            return null;
        }

        PathPoint[] pathPoint = { new PathPoint((int)vec3D.x, (int)vec3D.y, (int)vec3D.z) };
        return new SkyPathEntity(pathPoint);
    }

    @Override
    public boolean isFollowsThisPath(PathProvider pathProvider) {
        return this.getNavigation().l() == pathProvider;
    }

    @Override
    public void teleport(PathProvider pathProvider) {
        org.bukkit.World world = this.getBukkitEntity().getLocation().getWorld();
        Location location = new Location(world, pathProvider.getPath().x, pathProvider.getPath().y, pathProvider.getPath().z);
        this.getBukkitEntity().teleport(location);
    }

    /*
    @Override
    protected NavigationAbstract b(World world) {
        return new SkyNavigation(this, world);
    }
    */
}