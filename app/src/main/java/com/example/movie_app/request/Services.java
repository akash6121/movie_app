package com.example.movie_app.request;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.movie_app.utils.Credentials;
import com.example.movie_app.utils.MovieApi;

public class Services {
    private MovieApi movieApi;
    private Services(){
          Retrofit retrofit=new Retrofit.Builder().baseUrl(Credentials.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
          movieApi=retrofit.create(MovieApi.class);
    }
    private static Services service;
    public static Services getInstance(){
        if(service == null){
            service=new Services();
        }
        return service;
    }

    public MovieApi getMovieApi(){
        return movieApi;
    }
}
