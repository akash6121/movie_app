package com.example.movie_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.movie_app.models.MovieModal;
import com.example.movie_app.request.Services;
import com.example.movie_app.response.MovieSearchResponse;
import com.example.movie_app.utils.Credentials;
import com.example.movie_app.utils.MovieApi;
import com.example.movie_app.viewmodal.MovieViewModal;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MovieViewModal movieViewModal;
    private Services servent=Services.getInstance();
    GridView gridView;
    GridViewAdapter adapter;
    Toolbar appbar;
    EditText SearchMovie;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        WindowCompat.setDecorFitsSystemWindows(getWindow(),false);
        SearchMovie=findViewById(R.id.searchmovie);
        gridView=findViewById(R.id.showcase);
        appbar=findViewById(R.id.custombar);
        setSupportActionBar(appbar);
        movieViewModal=new ViewModelProvider(this).get(MovieViewModal.class);
        SearchMovie.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String data=SearchMovie.getText().toString();
                    SearchApi(data, 1);
                    CloseKeyboard();
                    SearchMovie.setVisibility(View.GONE);
                    return true;
                }
                return false;
            }
        });
        SearchApi("Avatar",1);
        // Calling the observer
        observechange();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        SearchMovie.setVisibility(View.VISIBLE);
        return super.onOptionsItemSelected(item);
    }

    private void observechange(){
        movieViewModal.getMovies().observe(this, new Observer<List<MovieModal>>() {
            @Override
            public void onChanged(List<MovieModal> movieModals) {
                if (movieModals!=null){
                    adapter=new GridViewAdapter(movieModals,MainActivity.this);
                    gridView.setAdapter(adapter);
                }
            }
        });
    }
    //Calling methods in main Activity
    public void SearchApi(String name,int number){
        Log.d("TAG", "SearchApi: "+name);
        movieViewModal.SearchApi(name,number);
    }
    private void CloseKeyboard(){
        View view = MainActivity.this.getCurrentFocus();
        if (view!=null){
            InputMethodManager manager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(),0);
//            view.clearFocus();
        }
    }
    public Call<MovieSearchResponse> getpopMovies(int number){
        return Services.getInstance().getMovieApi().getPopular(
                Credentials.API_KEY,
                number
        );
    }
    private void GetRetrofitRes() {
        MovieApi movieApi= servent.getMovieApi();
        Call<MovieSearchResponse> responseCall = movieApi.searchMovie(Credentials.API_KEY,"Avatar",1);
        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if(response.code()==200){
                    Log.d("TAG", "onResponse: "+response.body().toString());
                    List<MovieModal> movies=new ArrayList<>(response.body().getMovies());
                    for (MovieModal movie:movies){
                        Log.d("TAG", "Release date: "+movie.getRelease_date()+"  Movie Name  =  "+movie.getMovie_overview());
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });
    }
}