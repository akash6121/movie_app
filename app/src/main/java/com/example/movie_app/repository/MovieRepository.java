package com.example.movie_app.repository;

import com.example.movie_app.models.MovieModal;
import com.example.movie_app.request.MovieClient;

import java.util.List;

import androidx.lifecycle.LiveData;


public class MovieRepository {
    private static MovieRepository intance;
    private final MovieClient movieClient;
    private MovieRepository(){
        movieClient= MovieClient.getInstance();
    }
    public static MovieRepository getInstance(){
        if(intance==null){
            intance=new MovieRepository();
        }
        return intance;
    }
    public LiveData<List<MovieModal>> getMovies(){
        return movieClient.getMovies();
    }
    // Calling methods of movieclient
    public void SearchApi(String name,int number){
        movieClient.SearchApi(name,number);
    }
}
