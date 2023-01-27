package com.example.movie_app.response;

import com.example.movie_app.models.MovieModal;
// Single movie
public class MovieResponse {
    private MovieModal movieModal;
    private MovieModal getMovieModal(){
        return movieModal;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "movieModal=" + movieModal +
                '}';
    }
}
