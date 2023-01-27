package com.example.movie_app.viewmodal;

import com.example.movie_app.models.MovieModal;
import com.example.movie_app.repository.MovieRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MovieViewModal extends ViewModel{
    //Live Data
    private final MovieRepository movieRepository;

    public MovieViewModal(){
        movieRepository=    MovieRepository.getInstance();
    }

    public LiveData<List<MovieModal>> getMovies(){
        return movieRepository.getMovies();
    }
    //Calling methods of repository
    public void SearchApi(String name,int number){
        movieRepository.SearchApi(name,number);
    }
}
