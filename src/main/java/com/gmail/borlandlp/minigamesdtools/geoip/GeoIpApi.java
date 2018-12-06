package com.gmail.borlandlp.minigamesdtools.geoip;

import org.bukkit.entity.Player;

public interface GeoIpApi {
    GeoData requestGeoData(Player p);
}
