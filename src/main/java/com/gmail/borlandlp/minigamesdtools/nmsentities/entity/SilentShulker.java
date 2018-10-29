package com.gmail.borlandlp.minigamesdtools.nmsentities.entity;

import net.minecraft.server.v1_12_R1.EntityShulker;
import net.minecraft.server.v1_12_R1.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftShulker;
import org.bukkit.entity.Shulker;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class SilentShulker extends EntityShulker
{
    private final CraftShulker bukkitEntity;

    public SilentShulker(org.bukkit.World world)
    {
        super((World) ((CraftWorld) world).getHandle());
        this.bukkitEntity = new CraftShulker((CraftServer) Bukkit.getServer(), this);
    }
    @Override
    public CraftShulker getBukkitEntity()
    {
        return bukkitEntity;
    }

    protected void initAttributes()
    {
        super.initAttributes();
    }

    public Shulker spawn(final Location loc)
    {
        this.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        this.world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);

        return getBukkitEntity();
    }
}