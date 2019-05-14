package com.inmovie.inmovie.MovieTvClasses;

import java.util.ArrayList;
import java.util.List;

public class Actor {
    public static final String TMDB_IMAGE_PATH = "http://image.tmdb.org/t/p/w500";
    private String profilePic;
    private String role;
    private String actorName;
    private int age;
    private ArrayList<Entertainment> filmography;

    public Actor(String name){
        actorName = name;
    }

    public Actor(String name, int a){
        actorName = name;
        age = a;
    }

    public Actor(String name, String role, String profilePic){
        this.actorName = name;
        this.role = role;
        this.profilePic = profilePic;
    }

    public static class ActorResult {
        private List<Actor> results;

        public List<Actor> getResults() {
            return results;
        }
    }

    //add to his/her filmography
    public void addFilmography(Entertainment m){
        filmography.add(m);
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public void setFilmography(ArrayList<Entertainment> filmography) {
        this.filmography = filmography;
    }

    public ArrayList<Entertainment> getFilmography() {
        return filmography;
    }

    public String getProfilePic() {
        return TMDB_IMAGE_PATH + profilePic;
    }

    public String getRole() {
        return role;
    }

    public int getAge() {
        return age;
    }

    public String getActorName() {
        return actorName;
    }


}
