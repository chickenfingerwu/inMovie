package com.inmovie.inmovie.MovieTvClasses;

import java.util.ArrayList;
import java.util.List;

public class Crew {
    public static final String TMDB_IMAGE_PATH = "http://image.tmdb.org/t/p/w500";
    private String profilePic;
    private String role;
    private String crewName;
    private int age;
    private ArrayList<Movies> filmography;

    public Crew(String name){
        crewName = name;
    }

    public Crew(String name, int a){
        crewName = name;
        age = a;
    }

    public Crew(String name, String role, String profilePic){
        this.crewName = name;
        this.role = role;
        this.profilePic = profilePic;
    }

    public static class CrewResult {
        private List<Crew> results;

        public List<Crew> getResults() {
            return results;
        }
    }

    //add to his/her filmography
    public void addFilmography(Movies m){
        filmography.add(m);
    }

    public void setCrewName(String crewName) {
        this.crewName = crewName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public void setFilmography(ArrayList<Movies> filmography) {
        this.filmography = filmography;
    }

    public ArrayList<Movies> getFilmography() {
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

    public String getCrewName() {
        return crewName;
    }
}
