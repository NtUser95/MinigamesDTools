package com.gmail.borlandlp.minigamesdtools.geoip.ipstack;

import com.gmail.borlandlp.minigamesdtools.geoip.GeoData;
import com.gmail.borlandlp.minigamesdtools.geoip.LangData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class IpstackResponseParser {
    private JSONObject jsonObject;

    public IpstackResponseParser(JSONObject json) {
        this.jsonObject = json;
    }

    public GeoData parse() {
        GeoData geoData = new GeoData();

        geoData.ip = (String) jsonObject.get("ip");
        geoData.type = (String) jsonObject.get("type");
        geoData.continentCode = (String) jsonObject.get("continent_code");
        geoData.continentName = (String) jsonObject.get("continent_name");
        geoData.countryCode = (String) jsonObject.get("country_code");
        geoData.continentName = (String) jsonObject.get("country_name");

        JSONObject locSection = (JSONObject) jsonObject.get("location");
        geoData.locGeonameId = (String) locSection.get("geoname_id");
        geoData.locCapital = (String) locSection.get("capital");

        JSONArray langSection = (JSONArray) (locSection).get("languages");
        JSONObject langObj = null;
        for (int i = 0; i < langSection.size(); i++) {
            langObj = (JSONObject) langSection.get(i);
            LangData langData = new LangData();

            langData.langCode = (String) langObj.get("code");
            langData.langName = (String) langObj.get("name");
            langData.langNativeCode = (String) langObj.get("native");

            geoData.languages.add(langData);
        }

        return geoData;
    }
}
