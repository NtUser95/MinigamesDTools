package com.gmail.borlandlp.minigamesdtools.geoip.ipstack;

import com.gmail.borlandlp.minigamesdtools.util.HttpUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class IpstackRequestBuilder {
    private String ip;
    private String apiKey;

    public IpstackRequestBuilder setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public IpstackRequestBuilder setApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public IpstackResponse doRequest() {
        String strResponse = null;
        try {
            strResponse = HttpUtils.doGetRequest("http://api.ipstack.com/" + ip + "?access_key=" + apiKey);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        JSONObject json = null;
        try {
            json = (JSONObject) (new JSONParser().parse(strResponse));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        IpstackResponse response = new IpstackResponse();
        if(json.containsKey("success")) {
            JSONObject obj = (JSONObject) json.get("error");
            int errCode = (int) (Long.parseLong(obj.get("code").toString())); // err codes range 0 - 500
            response.setError(Errors.getByErrCode(errCode));
        } else {
            response.setResponse(json);
        }

        return response;
    }
}
