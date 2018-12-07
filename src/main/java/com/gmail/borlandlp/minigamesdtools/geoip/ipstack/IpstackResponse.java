package com.gmail.borlandlp.minigamesdtools.geoip.ipstack;

import com.gmail.borlandlp.minigamesdtools.geoip.GeoData;
import org.json.simple.JSONObject;

public class IpstackResponse {
    private Errors error;
    private JSONObject response;

    public void setResponse(JSONObject jsonObject) {
        this.response = jsonObject;
    }

    public Errors getError() {
        return error;
    }

    public void setError(Errors error) {
        this.error = error;
    }

    public boolean isSuccessful() {
        return error == null;
    }

    public GeoData getData() {
        return new IpstackResponseParser(this.response).parse();
    }
}
