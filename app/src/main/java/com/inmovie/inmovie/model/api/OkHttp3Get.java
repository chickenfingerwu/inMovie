package com.inmovie.inmovie.model.api;

import android.os.AsyncTask;

import org.json.simple.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttp3Get extends AsyncTask<String, Void, String>{
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected String doInBackground(String... strings) {
        Request.Builder builder = new Request.Builder();
        builder.url(strings[0]); // strings[0] is URL

        // Check if strings[1] exist. strings[1] is header.
        if (strings.length > 1) {
            JSONObject header = JSON.parseStringToJSON(strings[1]);
            for (Object key: header.keySet()) {
                builder.addHeader((String) key, (String) header.get(key));
            }
        }

        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
