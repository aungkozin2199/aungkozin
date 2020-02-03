package com.akz.clientapk;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FirebaseConnect {
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference categoryRef=db.collection("categories");
    CollectionReference movieRef=db.collection("movies");
    CollectionReference seriesRef=db.collection("series");
    static Context firebaseContext;
    static Activity firebaseActivity;
    public void getAllMovies(){
        movieRef.whereEqualTo("category","movies").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                ArrayList<MovieModel> movieModels=new ArrayList<MovieModel>();
                for (DocumentSnapshot snapshot:queryDocumentSnapshots){
                    movieModels.add(snapshot.toObject(MovieModel.class));

                }
                MovieRecyclerAdapter adapter=new MovieRecyclerAdapter(movieModels,firebaseContext,firebaseActivity);
                MovieFragment.rcView.setAdapter(adapter);

                GridLayoutManager gm=new GridLayoutManager(firebaseContext,3);
                MovieFragment.rcView.setLayoutManager(gm);


            }
        });
    }
    public void getAllSeries(){
        seriesRef.whereEqualTo("category","series").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                ArrayList<SeriesModel> seriesModels=new ArrayList<SeriesModel>();
                for (DocumentSnapshot snapshot:queryDocumentSnapshots){
                    seriesModels.add(snapshot.toObject(SeriesModel.class));
                }
                SeriesRecyclerAdapter adapter=new SeriesRecyclerAdapter(seriesModels,firebaseContext,firebaseActivity);
                SeriesFragment.rcView.setAdapter(adapter);

                GridLayoutManager gm=new GridLayoutManager(firebaseContext,3);
                SeriesFragment.rcView.setLayoutManager(gm);

            }
        });
    }

    public void getEpisode(String seriesname)
    {
        movieRef.whereEqualTo("series",seriesname).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                ArrayList<MovieModel> movieModels=new ArrayList<MovieModel>();
                for( DocumentSnapshot snapshot : queryDocumentSnapshots)
                {
                    movieModels.add(snapshot.toObject(MovieModel.class));
                }
                EpisodeAdapter adapter=new EpisodeAdapter(movieModels,firebaseContext,firebaseActivity);
                SeriesDetail.rcView.setAdapter(adapter);
                LinearLayoutManager lm=new LinearLayoutManager(firebaseContext, RecyclerView.VERTICAL,false);
                SeriesDetail.rcView.setLayoutManager(lm);
            }
        });
    }
}
