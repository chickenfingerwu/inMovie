package com.inmovie.inmovie.MovieTvClasses.TvClasses;

import java.util.ArrayList;
import java.util.List;

public class TvSeason {
    protected List<Episode> episodes;
    public TvSeason(List<Episode> e){
        this.episodes = e;
    }

    public TvSeason(){
        episodes = new ArrayList<>();
    }

    public void addEpisode(List<Episode> e, boolean clear){
        if(clear){
            episodes.clear();
        }
        episodes.addAll(e);
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }


}
