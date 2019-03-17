package com.inmovie.inmovie.model.api;

import com.inmovie.inmovie.BuildConfig;
import com.inmovie.inmovie.model.Poster;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class TheTVDB {
    private JSONObject auth;
    private String token;

    /**
     * Initialize TheTVDB api. Set API key, user key and username.
     */
    public TheTVDB() {
        auth = new JSONObject();
        auth.put("apikey", BuildConfig.TheTVDB_API_key);
        auth.put("userkey", BuildConfig.TheTVDB_user_key);
        auth.put("username", BuildConfig.TheTVDB_username);

        // Get token
        token = getToken();
    }

    static String getResponse(HttpURLConnection connection) {
        StringBuilder response = null;
        try {
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            response = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            bufferedReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    /**
     * Post JSON data to TheTVDB server and get response.
     * @param url URL to send data
     * @param json JSON data to send
     * @return response from server as JSON
     */
    private JSONObject postJSON(URL url, JSONObject json) {
        HttpsURLConnection connection = null;
        String data = json.toString();
        JSONObject jsonObject = null;

        try {
            // Initialize connection and set its properties
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST"); // Set method to POST
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            // Send data
            DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.writeBytes(data);
            dataOutputStream.flush();
            dataOutputStream.close();

            // Get and parse response to JSON
            jsonObject = JSON.parseStringToJSON(getResponse(connection));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) {
                connection.disconnect(); // Disconnect connection
            }
        }
        return jsonObject;
    }

    /**
     * Query from URL.
     * @param url query URL
     * @return response from server as JSON
     */
    private JSONObject query(String url) {
        JSONObject result = null;

        String[] strings = new String[2];
        strings[0] = url;
        strings[1] = "{\"Authorization\":\"Bearer " + token + "\"}";
        try {
            result = JSON.parseStringToJSON(new OkHttp3Get().execute(strings).get());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * If token expire, use this method to refresh it.
     */
    public void refreshToken() {
        token = getToken();
    }

    /**
     * Get TheTVDB API token.
     * @return token
     */
    private String getToken() {
        String _token = "";
        String url = "https://api.thetvdb.com/login";
        String[] strings = new String[2];
        strings[0] = url;
        strings[1] = auth.toJSONString();
        try {
            String response = new OkHttp3Post().execute(strings).get();
            _token = (String) JSON.parseStringToJSON(response).get("token");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return _token;
    }

    /**
     * Search a TV series based on name
     * @param name
     * @return JSON of TV series
     */
    public JSONArray searchByName(String name) {
        JSONObject result;
        String queryURL = "";

        try {
            queryURL = "https://api.thetvdb.com/search/series?name=" + URLEncoder.encode(name, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        result = query(queryURL);

        return (JSONArray) result.get("data");
    }

    /**
     * Search info for a series by its ID
     * @param id
     * @return Info of TV series
     */
    public JSONObject seriesInfoByID(int id) {
        JSONObject result = null;
        String requestURL = "https://api.thetvdb.com/series/" + id;
        result = query(requestURL);

        return (JSONObject) result.get("data");
    }

    /**
     * Search info for actors by series ID
     * @param id
     * @return List of actors
     */
    public JSONArray actorsInfoByID(int id) {
        JSONObject result = null;
        String requestURL = "https://api.thetvdb.com/series/" + id + "/actors";
        result = query(requestURL);

        return (JSONArray) result.get("data");
    }

    /**
     * Get episodes info by series ID
     * @param id
     * @return List of episodes
     */
    public JSONArray episodesInfoByID(int id) {
        JSONObject result = null;
        JSONArray res = null;

        String requestURL = "https://api.thetvdb.com/series/" + id + "/episodes";
        result = query(requestURL);
        res = (JSONArray) result.get("data");

        // Check if there are any other page
        long pages = (long) ((JSONObject) result.get("links")).get("last");
        for (long page = 2; page <= pages; page++) {
            JSONArray pageRes = null;
            result = query(requestURL + "?page=" + page);
            pageRes = (JSONArray) result.get("data");
            for (int i = 0; i < pageRes.size(); i++) {
                res.add(pageRes.get(i));
            }
        }

        return res;
    }

    /**
     * Get all poster links
     * @param id
     * @return Array of poster links
     */
    public ArrayList<Poster> getPostersByID(int id) {
        JSONObject result = null;
        JSONArray res = null;
        ArrayList<Poster> posters = new ArrayList<>();

        String requestURL = "https://api.thetvdb.com/series/" + id + "/images/query?keyType=poster";
        result = query(requestURL);

        res = (JSONArray) result.get("data");
        for (int i = 0; i < res.size(); i++) {
            posters.add(new Poster((JSONObject) res.get(i)));
        }

        return posters;
    }
}
