package com.akz.clientapk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class SeriesDetail extends AppCompatActivity {
static SeriesModel model;
static RecyclerView rcView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_detail);
        ImageView movieImage=findViewById(R.id.movieImage);
        TextView movieName=findViewById(R.id.movieName);
        Glide.with(getApplicationContext())
                .load(model.imageLink)
                .into(movieImage);
        movieName.setText(model.name);
        rcView=findViewById(R.id.rcview);
        FirebaseConnect.firebaseContext=getApplicationContext();
        FirebaseConnect.firebaseActivity=this;
        FirebaseConnect connect=new FirebaseConnect();
        connect.getEpisode(model.name);

    }
}
