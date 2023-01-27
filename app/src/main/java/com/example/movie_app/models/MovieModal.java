package com.example.movie_app.models;

public class MovieModal {
    private String title;
    private String poster_path;
    private String backdrop_path;
    private String release_date;
    private int id;
    private float vote_average;
    private String overview;

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public float getVote_average() {
        return vote_average;
    }

    public String getMovie_overview() {
        return overview;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }
}
