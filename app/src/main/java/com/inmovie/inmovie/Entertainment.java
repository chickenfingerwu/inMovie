package com.inmovie.inmovie;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class Entertainment {

    private String name;
    private String description;
    protected String poster_url;
    protected String banner_url;
    private int rating;

    public Entertainment() {
    }

    public Entertainment(String n){
        name = n;
    }

    public Entertainment(String n, String p_url, String b_url){
        name = n;
        try {
            poster_url = p_url;
            banner_url = b_url;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public Entertainment(String n, String p_url, String b_url, String des){
        name = n;
        description = des;
        try {
            poster_url = p_url;
            banner_url = b_url;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addDescription(String des){
        description = des;
    }

    public String getPoster_url(){
        return poster_url;
    }

    public String getBanner_url(){
        return banner_url;
    }

    public String getName(){
        return name;
    }
}
