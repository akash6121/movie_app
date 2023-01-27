package com.example.movie_app.response;

import com.example.movie_app.models.MovieModal;
import com.google.gson.annotations.SerializedName;

import java.util.List;

// Popular movies
public class MovieSearchResponse {
    @SerializedName("total_results")
    private int total_count;
    @SerializedName("results")
    private List<MovieModal> movies;

    public int getTotal_count() {
        return total_count;
    }

    public List<MovieModal> getMovies() {
        return movies;
    }
    public List<MovieModal> setMovies() {
        return movies;
    }

    @Override
    public String toString() {
        return "MovieSearchResponse{" +
                "total_count=" + total_count +
                ", movies=" + movies +
                '}';
    }
}
