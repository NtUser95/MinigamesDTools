package com.gmail.borlandlp.minigamesdtools.util;

import net.minecraft.server.v1_12_R1.ChatMessageType;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import org.apache.commons.codec.digest.DigestUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.Metadatable;

import java.lang.reflect.Method;
import java.util.*;

public class ArenaUtils {

    public static String generateRandID() {
        return DigestUtils.md5Hex(((new Random()).nextFloat() * 100000000) + "");
    }

    //code MCore utils
    public static boolean isNpc(Object object)
    {
        if ( ! (object instanceof Metadatable)) return false;
        Metadatable metadatable = (Metadatable)object;
        try
        {
            return metadatable.hasMetadata("NPC");
        }
        catch (UnsupportedOperationException e)
        {
            // ProtocolLib
            // UnsupportedOperationException: The method hasMetadata is not supported for temporary players.
            return false;
        }

    }

    public static void sendMessage(Player player, ChatMessageType chatMessageType, String message) {
        IChatBaseComponent comp = IChatBaseComponent.ChatSerializer
                .a("{\"text\":\"" + ChatColor.RED + message + "\"}");
        PacketPlayOutChat packet = new PacketPlayOutChat(comp, chatMessageType);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
    }

    public static Entity getNearestEntity(Location location, List<Entity> entityCollection) {
        Map<Entity, Integer> differences = new HashMap<>();
        for(Entity entity : entityCollection) {
            int diffX = (int)Math.abs(Math.abs(location.getX()) - Math.abs(entity.getLocation().getX()));
            int diffY = (int)Math.abs(Math.abs(location.getY()) - Math.abs(entity.getLocation().getY()));
            int diffZ = (int)Math.abs(Math.abs(location.getZ()) - Math.abs(entity.getLocation().getZ()));
            differences.put(entity, diffX + diffY + diffZ);
        }

        Map<Entity, Integer> sortDifferences = MapHelper.sortByValue(differences);
        return (Entity)sortDifferences.keySet().toArray()[0];
    }

    public static Location str2Loc(String[] str) {
        double x = Integer.parseInt(str[0]);
        double y = Integer.parseInt(str[1]);
        double z = Integer.parseInt(str[2]);
        World world = Bukkit.getWorld(str[3]);
        Location loc = new Location(world, x, y, z);
        if(str.length == 5) {
            loc.setYaw(Integer.parseInt(str[4]));
        }
        if(str.length == 6) {
            loc.setYaw(Integer.parseInt(str[5]));
        }

        return loc;
    }

    public static String[] loc2Str(Location loc) {
        List<String> strs = new ArrayList<>();
        strs.add(loc.getBlockX() + "");
        strs.add(loc.getBlockY() + "");
        strs.add(loc.getBlockZ() + "");
        strs.add(loc.getWorld().getName());
        strs.add(loc.getYaw() + "");
        strs.add(loc.getPitch() + "");

        return strs.toArray(new String[strs.size()]);
    }
}
