package com.gmail.borlandlp.minigamesdtools.geoip;

import com.gmail.borlandlp.minigamesdtools.APIComponent;
import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath;
import com.gmail.borlandlp.minigamesdtools.geoip.ipstack.IpstackRequestBuilder;
import com.gmail.borlandlp.minigamesdtools.geoip.ipstack.IpstackResponse;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class GeoIp implements GeoIpApi, APIComponent {
    private String apiKey;

    public GeoData requestGeoData(Player p) {
        String ip = p.getAddress().getHostName();
        IpstackResponse response = new IpstackRequestBuilder().setApiKey(this.apiKey).setIp(ip).doRequest();
        if(!response.isSuccessful()) {
            Debug.print(Debug.LEVEL.WARNING, "GeoIPRequest error msg:" + response.getError().getErrMessage());
            return null;
        } else {
            return response.getData();
        }
    }

    @Override
    public void onLoad() {
        ConfigurationSection config = MinigamesDTools.getInstance().getConfigProvider().getEntity(ConfigPath.MAIN, "minigamesdtools").getData();
        this.apiKey = config.getString("geoip.key");
    }

    @Override
    public void onUnload() {

    }
}
