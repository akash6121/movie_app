package com.example.movie_app.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movie_app.R;
import com.example.movie_app.models.MovieModal;
import com.example.movie_app.viewmodal.MovieViewModal;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.MaterialToolbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SingleMovieScreen extends AppCompatActivity {

    private MovieViewModal viewModalsingle;
    List<MovieModal> movieModalList;
    int position;
    ImageView poster;
    TextView overview;
    ImageView appbarimg;
    TextView Release_date;
    TextView Month_Name,Day_Name,RatText;
    RatingBar Star;
    CollapsingToolbarLayout Col_top;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_movie_screen);

//        WindowCompat.setDecorFitsSystemWindows(getWindow(),false);

        //Getting Id's
        poster = findViewById(R.id.singleimage);
        overview = findViewById(R.id.overview);
        appbarimg = findViewById(R.id.appbarimg);
        Release_date = findViewById(R.id.releasedate);
        Star = findViewById(R.id.rating);
        RatText = findViewById(R.id.rattext);
        Month_Name=findViewById(R.id.monthname);
        Day_Name=findViewById(R.id.dayname);
        Col_top=findViewById(R.id.collapsed);
        MaterialToolbar topApp=findViewById(R.id.topappbar);
        setSupportActionBar(topApp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);

        //Getting data from previous screen
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);

        //Getting api data
        viewModalsingle = new ViewModelProvider(this).get(MovieViewModal.class);
        movieModalList = viewModalsingle.getMovies().getValue();
        Col_top.setTitle(movieModalList.get(position).getTitle());
        Col_top.setExpandedTitleColor(Color.WHITE);
        Col_top.setStatusBarScrimColor(Color.rgb(1,135,134));

        //Setting image
        String imgurl = "https://image.tmdb.org/t/p/w500" + movieModalList.get(position).getPoster_path();
        String imgappbar = "https://image.tmdb.org/t/p/w500" + movieModalList.get(position).getBackdrop_path();
        Glide.with(this).load(imgurl).placeholder(R.drawable.ic_baseline_downloading_24).error(R.drawable.ic_baseline_image_not_supported_24).into(poster);
        Glide.with(this).load(imgappbar).placeholder(R.drawable.ott).into(appbarimg);

        //SET text
        if(movieModalList.get(position).getMovie_overview().equals("")){
            overview.setVisibility(View.GONE);
        }
        overview.setText("Overview : "+movieModalList.get(position).getMovie_overview());
        if(movieModalList.get(position).getVote_average()==0){
            Star.setVisibility(View.INVISIBLE);
            RatText.setVisibility(View.INVISIBLE);
        }
        Star.setIsIndicator(true);
        Star.setRating(movieModalList.get(position).getVote_average() / 2);
        Release_date.setText("Release Date : "+movieModalList.get(position).getRelease_date());

        //Get month name
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        int month = Integer.parseInt(movieModalList.get(position).getRelease_date().substring(5, 7));
        calendar.set(Calendar.MONTH, month - 1);
        String month_name = month_date.format(calendar.getTime());
        Month_Name.setText("Month Name : "+month_name);

        //Getting day name
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(movieModalList.get(position).getRelease_date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat day = new SimpleDateFormat("EEEE");
        String day_name = day.format(date);
        Day_Name.setText("Day Name : "+day_name);
    }
}