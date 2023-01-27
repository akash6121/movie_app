package com.example.movie_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.movie_app.models.MovieModal;
import com.example.movie_app.view.SingleMovieScreen;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

public class GridViewAdapter extends BaseAdapter {
    List<MovieModal> datalist;
    Context context;
    GridViewAdapter(List<MovieModal> datalist,Context context){
        this.datalist=datalist;
        this.context=context;
    }
    @Override
    public int getCount() {
        return datalist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item,parent,false);
        TextView movietitle=view.findViewById(R.id.movname);
        movietitle.setText(datalist.get(position).getTitle());
        String imgurl="https://image.tmdb.org/t/p/w500"+datalist.get(position).getPoster_path();
        ImageView movieimage=view.findViewById(R.id.movieimg);
        Glide.with(parent.getContext()).load(imgurl).placeholder(R.drawable.ic_baseline_downloading_24).error(R.drawable.ic_baseline_image_not_supported_24).into(movieimage);
        CardView singleitem=view.findViewById(R.id.singlecard);
        singleitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context,datalist.get(position).getMovie_overview(),Toast.LENGTH_LONG).show();
                Intent intent=new Intent(context, SingleMovieScreen.class);
                intent.putExtra("position",position);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
