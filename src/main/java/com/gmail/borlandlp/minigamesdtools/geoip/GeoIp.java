package com.gmail.borlandlp.minigamesdtools.geoip;

import com.gmail.borlandlp.minigamesdtools.APIComponent;
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath;
import com.gmail.borlandlp.minigamesdtools.util.HttpUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GeoIp implements GeoIpApi, APIComponent {
    private String apiKey;

    public GeoData requestGeoData(Player p) {
        String ip = p.getAddress().getHostName();

        String response = null;
        try {
            response = HttpUtils.doGetRequest("http://api.ipstack.com/" + ip + "?access_key=" + apiKey);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        JSONObject json = null;
        try {
            json = (JSONObject) (new JSONParser().parse(response));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
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
