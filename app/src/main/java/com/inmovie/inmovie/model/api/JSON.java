package com.inmovie.inmovie.model.api;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSON {
    /**
     * Convert String to JSONObject
     * @param s String
     * @return JSON
     */
    static JSONObject parseStringToJSON(String s) {
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) new JSONParser().parse(s);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
