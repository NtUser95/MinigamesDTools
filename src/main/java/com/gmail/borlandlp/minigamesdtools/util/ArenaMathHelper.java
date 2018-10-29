package com.gmail.borlandlp.minigamesdtools.util;

import net.minecraft.server.v1_12_R1.Vec3D;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ArenaMathHelper {
    public static double degreesBetweenPlayerAndLocation(Player player, Location targetLoc) {
        Vec3D reqVec = ArenaMathHelper.getVec2Location(player.getLocation(), targetLoc);
        return ArenaMathHelper.degreesBetweenTwoVectors_XZ(reqVec, player.getLocation().getDirection());
    }

    public static double degreesBetweenTwoVectors_XZ(Vec3D vec1,  Vec3D vec2) {
        return Math.toDegrees(getAngle(vec1.x, vec1.z, vec2.x, vec2.z));
    }

    public static double degreesBetweenTwoVectors_XZ(Vec3D vec1, Vector vec2) {
        return Math.toDegrees(getAngle(vec1.x, vec1.z, vec2.getX(), vec2.getZ()));
    }

    public static double degreesBetweenTwoVectors_XZ(Vector vec1, Vector vec2) {
        return Math.toDegrees(getAngle(vec1.getX(), vec1.getZ(), vec2.getX(), vec2.getZ()));
    }

    public static double degreesBetweenTwoVectors_XZ(Vector vec1, Vec3D vec2) {
        return Math.toDegrees(getAngle(vec1.getX(), vec1.getZ(), vec2.x, vec2.z));
    }

    public static double getAngle(double x1, double y1, double x2, double y2) {
        double t = (x1*x2+y1*y2) / (Math.sqrt(x1*x1+y1*y1) * Math.sqrt(x2*x2+y2*y2));
        if (t < -1) {
            t =- 1;
        } else if(t > 1) {
            t = 1;
        }

        return Math.acos(t);
    }

    public static Vec3D getVec2Location(Location from, Location to) {
        double x = to.getX() - from.getX();
        double y = to.getY() - from.getY();
        double z = to.getZ() - from.getZ();

        return new Vec3D(x, y, z);
    }

    public static Interposition getInterpositionOfPoint(Location fromLoc, Location toLoc, Location point) {
        return getInterpositionOfPoint(fromLoc.getX(), fromLoc.getZ(), toLoc.getX(), toLoc.getZ(), point.getX(), point.getZ());
    }

    public static Interposition getInterpositionOfPoint(double fromX, double fromZ, double toX, double toZ, double pointX, double pointZ) {
        //(х3 - х1) * (у2 - у1) - (у3 - у1) * (х2 - х1)
        double d = (pointX - fromX) * (toZ - fromZ) - (pointZ - fromZ) * (toX - fromX);
        if(d == 0) {
            return Interposition.UPSIDE;
        } else if(d > 0) {
            return Interposition.LEFTSIDE;
        } else {
            return Interposition.RIGHTSIDE;
        }
    }

    public enum Interposition {
        UPSIDE,
        RIGHTSIDE,
        LEFTSIDE,
    }
}
