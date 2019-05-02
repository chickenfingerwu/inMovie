package com.inmovie.inmovie.model.api;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class CheckConnectivity extends AsyncTask<Void, Void, Integer> {
    @Override
    protected Integer doInBackground(Void... voids) {
        int canConnect = 1;
        // 0: no Internet
        // 1: can't connect to TMDb and OMDb
        // 3: can't connect to OMDb
        // 5: can't connect to TMDb
        // 7: can connect to TMDb and OMDb

        try {
            URL url = new URL("https://api.themoviedb.org/3/");
            URLConnection connection = url.openConnection();
            connection.connect();
            canConnect += 2;
            url = new URL("http://www.omdbapi.com/?apikey=[yourkey]&");
            connection = url.openConnection();
            connection.connect();
            canConnect += 4;
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return canConnect;
    }
}
