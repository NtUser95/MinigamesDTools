package com.gmail.borlandlp.minigamesdtools.nmsentities.entity;

import com.gmail.borlandlp.minigamesdtools.nmsentities.classes.PathProvider;
import com.gmail.borlandlp.minigamesdtools.nmsentities.classes.SkyDragonControllerCharge;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEnderDragon;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.lang.reflect.Field;

public class SkyDragon extends EntityEnderDragon implements IMoveControllableEntity, IControllable, ISkybattleEntity {
    private CraftEnderDragon bukkitEntity;
    private DragonControllerManager dragonControllerManager;

    public SkyDragon(org.bukkit.World world) {
        super((World) ((CraftWorld) world).getHandle());
        this.children = new EntityComplexPart[]{this.bw, this.bx, this.by, this.bz, this.bA, this.bB, this.bC, this.bD};
        this.setHealth(this.getMaxHealth());
        this.setSize(16.0F, 8.0F);
        this.noclip = true;
        this.fireProof = true;
        this.setNoAI(false);


        try {
            Field fbM = EntityEnderDragon.class.getDeclaredField("bM");
            fbM.setAccessible(true);
            fbM.setInt(this, 100);

            Field fbK = EntityEnderDragon.class.getDeclaredField("bK");
            fbK.setAccessible(true);
            if (!((CraftWorld) world).getHandle().isClientSide && ((CraftWorld) world).getHandle().worldProvider instanceof WorldProviderTheEnd) {
                fbK.set(this, ((WorldProviderTheEnd)((CraftWorld) world).getHandle().worldProvider).t());
            } else {
                fbK.set(this, null);
            }

            this.dragonControllerManager = new DragonControllerManager(this);
            Field fbL = EntityEnderDragon.class.getDeclaredField("bL");
            fbL.setAccessible(true);
            fbL.set(this, this.getDragonControllerManager());
        } catch (NoSuchFieldException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }


        this.ah = true;
        this.bukkitEntity = new CraftEnderDragon((CraftServer) Bukkit.getServer(), this);
    }

    @Override
    public DragonControllerManager getDragonControllerManager() {
        return dragonControllerManager;
    }

    @Override
    public void setPath(PathProvider path) {
        DragonControllerPhase dragoncontrollerphase = CraftEnderDragon.getMinecraftPhase(CraftEnderDragon.getBukkitPhase(DragonControllerPhase.i));
        //IDragonController iDragonController = dragoncontrollerphase.a(this);

        Field curDragContr = null;
        try {
            curDragContr = this.getDragonControllerManager().getClass().getDeclaredField("currentDragonController");
        } catch (NoSuchFieldException e1) {
            e1.printStackTrace();
        }
        curDragContr.setAccessible(true);
        try {
            curDragContr.set(this.getDragonControllerManager(), (SkyDragonControllerCharge) path);
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
        this.getDataWatcher().set(EntityEnderDragon.PHASE, dragoncontrollerphase.b());
    }

    @Override
    public PathProvider vec2Path(Vec3D target) {
        SkyDragonControllerCharge iDragonController = new SkyDragonControllerCharge(this);
        iDragonController.a(target);

        return iDragonController;
    }

    @Override
    public boolean isFollowsThisPath(PathProvider pathProvider) {
        return this.getDragonControllerManager().a() == pathProvider;
    }

    @Override
    public CraftEnderDragon getBukkitEntity()
    {
        return bukkitEntity;
    }

    protected void initAttributes()
    {
        super.initAttributes();
    }

    public EnderDragon spawn(Location loc) {
        this.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        this.world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);

        return getBukkitEntity();
    }

    @Override
    public void teleport(PathProvider pathProvider) {
        org.bukkit.World world = this.getBukkitEntity().getLocation().getWorld();
        Location location = new Location(world, pathProvider.getPath().x, pathProvider.getPath().y, pathProvider.getPath().z);
        this.getBukkitEntity().teleport(location);
    }

    //onLivingUpdate();
    /*public void n() {
        // dragon living
    }*/
}
