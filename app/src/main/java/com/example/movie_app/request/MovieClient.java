package com.example.movie_app.request;

import android.util.Log;

import com.example.movie_app.AppExecuters;
import com.example.movie_app.models.MovieModal;
import com.example.movie_app.response.MovieSearchResponse;
import com.example.movie_app.utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Response;

public class MovieClient {
    private MutableLiveData<List<MovieModal>> MovieLive;
    private static MovieClient instance;
    private RetrieveMovieData retrieveMovieData;
    private MovieClient(){
        MovieLive=new MutableLiveData<>();
    }
    public static MovieClient getInstance(){
        if(instance==null) {
            instance=new MovieClient();
        }
        return instance;
    }
    public LiveData<List<MovieModal>> getMovies(){
        return MovieLive;
    }

    public void SearchApi(String name,int number){
        Log.d("TAG", "SearchApi: "+name);
            retrieveMovieData = new RetrieveMovieData(name, number);
        final Future handler= AppExecuters.getInstance().getExecutorService().submit(retrieveMovieData);
        AppExecuters.getInstance().getExecutorService().schedule(new Runnable() {
            @Override
            public void run() {
                // Terminate the call if response not obtained
                handler.cancel(true);
            }
        },10, TimeUnit.SECONDS);
    }
    // Retrieving data from api using runnable class
    private class RetrieveMovieData implements Runnable{
        private String name;
        private int number;
        boolean cancel;

        public RetrieveMovieData(String name, int number) {
            this.name = name;
            this.number = number;
            cancel=false;
        }

        @Override
        public void run() {
            // Getting the response object
            try {
                Response response=getMovies(name,number).execute();
                if (cancel)
                    return;
                if (response.code()==200){
                    List<MovieModal> list=new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    if(number==1){
                        MovieLive.postValue(list);
                    }else {
                        List<MovieModal> currentmovies=MovieLive.getValue();
                        currentmovies.addAll(list);
                        MovieLive.postValue(currentmovies);
                    }
                }else {
                    String error=response.errorBody().string();
                    Log.e("TAG", "errorLOG: "+error );
                    MovieLive.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                MovieLive.postValue(null);
            }
        }
            // Search method
            private Call<MovieSearchResponse> getMovies(String name,int number){
                Log.d("TAG", "getMovies: Called Success"+name);
                return Services.getInstance().getMovieApi().searchMovie(
                        Credentials.API_KEY,
                        name,
                        number
                );
            }
            private void CancelRequest(){
                Log.d("TAG", "Cancelling Search Request");
                cancel=true;
            }
    }
}
