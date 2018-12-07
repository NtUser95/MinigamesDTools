package com.gmail.borlandlp.minigamesdtools.geoip;

import java.util.ArrayList;
import java.util.List;

public class GeoData {
    public String ip;
    public String type; // ipv4, ipv6
    public String continentCode; // Europe..
    public String continentName;
    public String countryCode; // EN, RU..
    public String countryName; // United Kingdom, Russia..

    public String locGeonameId;
    public String locCapital; // London, Moscow..

    public List<LangData> languages = new ArrayList<>();
}
