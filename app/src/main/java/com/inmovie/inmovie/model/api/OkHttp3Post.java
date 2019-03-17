package com.inmovie.inmovie.model.api;

import android.os.AsyncTask;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttp3Post extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... strings) {
        String result = "";

        try {
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(JSON, strings[1]);
            Request request = new Request.Builder().url(strings[0]).post(body).build();

            Response response = client.newCall(request).execute();
            result = response.body().string();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
