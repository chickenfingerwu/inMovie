package com.inmovie.inmovie;

import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;

import com.inmovie.inmovie.TVclasses.TvShow;

import org.json.JSONObject;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class BannerPhotoThreadManager {
    ImageView banner;
    TextView name;
    TextView additionalInfo;
    TvShow show;
    JSONObject tvData;
    Handler handler;

    public BannerPhotoThreadManager(){
        handler = new Handler() {
            @Override
            public void publish(LogRecord record) {

            }

            @Override
            public void flush() {

            }

            @Override
            public void close() throws SecurityException {

            }
        };
    }

    public BannerPhotoThreadManager(ImageView b, TextView n, TextView a){
        banner = b;
        name = n;
        additionalInfo = a;
    }

    public void handleSettingBanner(){
        try {
            String bannerUrl = tvData.getString("backdrop_path");
            show.setBackdrop(bannerUrl);

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }


    public ImageView getBanner() {
        return banner;
    }

    public TextView getAdditionalInfo() {
        return additionalInfo;
    }

    public TextView getName() {
        return name;
    }

    public void setAdditionalInfo(TextView additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public void setBanner(ImageView banner) {
        this.banner = banner;
    }

    public void setName(TextView name) {
        this.name = name;
    }
}
