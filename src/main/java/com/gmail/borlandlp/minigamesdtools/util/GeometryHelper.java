package com.gmail.borlandlp.minigamesdtools.util;

import com.gmail.borlandlp.minigamesdtools.util.geometry.GeneratedBlockSchema;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;
import java.util.List;

public class GeometryHelper {
    public static GeneratedBlockSchema generateSquare(Location centerBlock, int radius, boolean hollow) {
        int cx = centerBlock.getBlockX();
        int cy = centerBlock.getBlockY();
        int cz = centerBlock.getBlockZ();
        List<Location> borderBlocks = new ArrayList<>();
        List<Location> fillerBlocks = new ArrayList<>();

        for (int x = cx - radius; x <= cx + radius; x++) {
            for (int y = cy - radius; y <= cy + radius; y++) {
                for (int z = cz - radius; z <= cz + radius; z++) {
                    if (y == cy + radius || y == cy - radius || x == cx + radius || x == cx - radius || z == cz + radius || z == cz - radius) {
                        borderBlocks.add(new Location(centerBlock.getWorld(), x, y, z));
                    } else if(!hollow) {
                        fillerBlocks.add(new Location(centerBlock.getWorld(), x, y, z));
                    }
                }
            }
        }

        return new GeneratedBlockSchema(borderBlocks, fillerBlocks);
    }

    public static GeneratedBlockSchema generateFlatSquare(Location centerBlock, int radius, boolean hollow) {
        int cx = centerBlock.getBlockX();
        int cy = centerBlock.getBlockY();
        int cz = centerBlock.getBlockZ();
        List<Location> boundBlocks = new ArrayList<>();
        List<Location> fillerBlocks = new ArrayList<>();

        for (int x = cx - radius; x <= cx + radius; x++) {
            for (int z = cz - radius; z <= cz + radius; z++) {
                if (x == cx + radius || x == cx - radius || z == cz + radius || z == cz - radius) {
                    boundBlocks.add(new Location(centerBlock.getWorld(), x, cy, z));
                } else if(!hollow) {
                    fillerBlocks.add(new Location(centerBlock.getWorld(), x, cy, z));
                }
            }
        }

        return new GeneratedBlockSchema(boundBlocks, fillerBlocks);
    }

    /*
    * ToDo: Hollow isnt used.
    * */
    public static List<Location> generateCylinder(Location centerBlock, int radius, boolean hollow) {
        int cx = centerBlock.getBlockX();
        int cy = centerBlock.getBlockY();
        int cz = centerBlock.getBlockZ();

        int rSquared = radius * radius;
        List<Location> blocks = new ArrayList<>();

        for (int x = cx - radius; x <= cx +  radius; x++) {
            for (int y = cy - radius; y <= cy +  radius; y++) {
                for (int z = cz - radius; z <= cz + radius; z++) {
                    if ((cx - x) * (cx -x) + (cz - z) * (cz - z) <= rSquared) {
                        blocks.add(new Location(centerBlock.getWorld(), x, y, z));
                    }
                }
            }
        }

        return blocks;
    }

    public static GeneratedBlockSchema generateSphere(Location centerBlock, int radius, boolean hollow) {
        List<Location> borderBlocks = new ArrayList<>();
        List<Location> fillerBlock = new ArrayList<>();

        int bx = centerBlock.getBlockX();
        int by = centerBlock.getBlockY();
        int bz = centerBlock.getBlockZ();

        for(int x = bx - radius; x <= bx + radius; x++) {
            for(int y = by - radius; y <= by + radius; y++) {
                for(int z = bz - radius; z <= bz + radius; z++) {
                    double distance = ((bx-x) * (bx-x) + ((bz-z) * (bz-z)) + ((by-y) * (by-y)));
                    if(distance < radius * radius && distance >= ((radius - 1) * (radius - 1))) {
                        borderBlocks.add(new Location(centerBlock.getWorld(), x, y, z));
                    } else if(!hollow && distance < ((radius - 1) * (radius - 1))) {
                        fillerBlock.add(new Location(centerBlock.getWorld(), x, y, z));
                    }
                }
            }
        }

        return new GeneratedBlockSchema(borderBlocks, fillerBlock);
    }

    public static GeneratedBlockSchema generateFlatSphere(Location centerBlock, BlockFace direction, int radius, boolean hollow) {
        List<Location> borderBlocks = new ArrayList<>();
        List<Location> fillerBlocks = new ArrayList<>();

        if(direction == BlockFace.DOWN || direction == BlockFace.UP) {
            int bx = centerBlock.getBlockX();
            int bz = centerBlock.getBlockZ();

            for(int x = bx - radius; x <= bx + radius; x++) {
                for(int z = bz - radius; z <= bz + radius; z++) {
                    double distance = ((bx-x) * (bx-x) + ((bz-z) * (bz-z)));
                    if(distance < radius * radius && distance >= ((radius - 1) * (radius - 1))) {
                        borderBlocks.add(new Location(centerBlock.getWorld(), x, centerBlock.getBlockY(), z));
                    } else if(!hollow && distance < ((radius - 1) * (radius - 1))) {
                        fillerBlocks.add(new Location(centerBlock.getWorld(), x, centerBlock.getBlockY(), z));
                    }
                }
            }
        } else if(direction == BlockFace.EAST || direction == BlockFace.WEST) {
            int by = centerBlock.getBlockY();
            int bz = centerBlock.getBlockX();

            for(int y = by - radius; y <= by + radius; y++) {
                for(int z = bz - radius; z <= bz + radius; z++) {
                    double distance = ((by-y) * (by-y) + ((bz-z) * (bz-z)));
                    if(distance < radius * radius && distance >= ((radius - 1) * (radius - 1))) {
                        borderBlocks.add(new Location(centerBlock.getWorld(), z, y, centerBlock.getBlockZ()));
                    } else if(!hollow && distance < ((radius - 1) * (radius - 1))) {
                        fillerBlocks.add(new Location(centerBlock.getWorld(), z, y, centerBlock.getBlockZ()));
                    }
                }
            }
        } else {
            int by = centerBlock.getBlockY();
            int bz = centerBlock.getBlockZ();

            for(int y = by - radius; y <= by + radius; y++) {
                for(int z = bz - radius; z <= bz + radius; z++) {
                    double distance = ((by-y) * (by-y) + ((bz-z) * (bz-z)));
                    if(distance < radius * radius && distance >= ((radius - 1) * (radius - 1))) {
                        borderBlocks.add(new Location(centerBlock.getWorld(), centerBlock.getBlockX(), y, z));
                    } else if(!hollow && distance < ((radius - 1) * (radius - 1))) {
                        fillerBlocks.add(new Location(centerBlock.getWorld(), centerBlock.getBlockX(), y, z));
                    }
                }
            }
        }

        return new GeneratedBlockSchema(borderBlocks, fillerBlocks);
    }
}
