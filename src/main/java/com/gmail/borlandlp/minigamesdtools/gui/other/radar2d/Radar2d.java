package com.gmail.borlandlp.minigamesdtools.gui.other.radar2d;

import com.gmail.borlandlp.minigamesdtools.util.ArenaMathHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Radar2d implements Radar {
    private List<Marker> markers = new ArrayList<>();
    private int drawDistance = 30; // blocks
    private Player viewer;
    private BossBar bossBar;
    private int radarSize = 30;// 15 symbols <- 1 middle symbol -> 15 symbols

    public Radar2d(BarStyle style, BarColor color) {
        this.bossBar = Bukkit.createBossBar("Radar2d", color, style);
    }

    public void draw() {
        double maxDegreesDiff = 90;

        /*
        * Если размер радара чётный(к примеру - 20), то (middleCell + drawSymbol) спровоцирует выход за границы массива.
        * (middleCell - drawSymbol) = 10 - 10 = 0 // В пределах массива
        * (middleCell + drawSymbol) = 10 + 10 = 20 // Массив заканчивается на 19 индексе, начинается на - 0.
        * Поэтому, к чётным массивам мы прибавляем +1
        * */
        String[] strs = new String[(this.radarSize % 2) == 0 ? this.radarSize + 1 : this.radarSize];

        for (Marker marker : this.markers) {
            Location viewerLocation = this.viewer.getLocation();
            if(viewerLocation.distance(marker.getLocation()) > this.drawDistance) {
                continue;
            }

            Vector hopVec = viewerLocation.clone().getDirection().multiply(this.drawDistance);
            Location hopLoc = viewerLocation.clone().add(hopVec);
            ArenaMathHelper.Interposition interposition = ArenaMathHelper.getInterpositionOfPoint(viewerLocation, hopLoc, marker.getLocation());

            double currentDegreesDiff = ArenaMathHelper.degreesBetweenPlayerAndLocation(this.viewer, marker.getLocation());
            double degreesDiffPercent = (currentDegreesDiff / (maxDegreesDiff * 2D)) * 100D;
            int drawSymbol = (int)((degreesDiffPercent / 100D) * this.radarSize);

            int middleCell = this.radarSize / 2;
            if((middleCell - drawSymbol) < 0) { // out of bound
                continue;
            }

            if(interposition == ArenaMathHelper.Interposition.LEFTSIDE) {
                int charPosition = middleCell - drawSymbol;
                strs[charPosition] = marker.getColor() + "#";
            } else if(interposition == ArenaMathHelper.Interposition.RIGHTSIDE) {
                int charPosition = middleCell + drawSymbol;
                strs[charPosition] = marker.getColor() + "#";
            } else if(interposition == ArenaMathHelper.Interposition.UPSIDE) {
                strs[middleCell] = ChatColor.RED + "#";
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.radarSize; i++) {
            if(strs[i] == null) {
                stringBuilder.append(ChatColor.WHITE).append("_");
            } else {
                stringBuilder.append(strs[i]);
            }
        }

        bossBar.setTitle(stringBuilder.toString());
    }

    public int getDrawDistance() {
        return drawDistance;
    }

    public int getRadarSize() {
        return radarSize;
    }

    public void setDrawDistance(int drawDistance) {
        this.drawDistance = drawDistance;
    }

    public void setRadarSize(int radarSize) {
        this.radarSize = radarSize;
    }

    public Player getViewer() {
        return viewer;
    }

    public void setViewer(Player player) {
        this.viewer = player;
        this.bossBar.addPlayer(player);
    }

    public void addMarker(Marker marker) {
        this.markers.add(marker);
    }

    public void removeMarker(Marker marker) {
        this.markers.remove(marker);
    }

    public Marker getCurrentMarker(Block block) {
        return this.markers.stream().filter(m -> m.isOwner(block)).findFirst().orElse(null);
    }

    public Marker getCurrentMarker(Entity entity) {
        return this.markers.stream().filter(m -> m.isOwner(entity)).findFirst().orElse(null);
    }

    @Override
    public void onUnload() {
        this.bossBar.removePlayer(this.getViewer());
    }

    @Override
    public void onLoad() {

    }
}
