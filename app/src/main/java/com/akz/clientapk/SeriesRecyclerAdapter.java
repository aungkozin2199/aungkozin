package com.akz.clientapk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;

public class SeriesRecyclerAdapter  extends RecyclerView.Adapter<SeriesRecyclerAdapter.SeriesHolder> {
    private InterstitialAd mInterstitialAd;


    ArrayList<SeriesModel> seriesModels=new ArrayList<>();
    Context context;
    Activity myActivity;

    public SeriesRecyclerAdapter(ArrayList<SeriesModel> SeriesModels, Context context, Activity myActivity) {
        this.seriesModels = SeriesModels;
        this.context = context;
        this.myActivity = myActivity;
    }

    @NonNull
    @Override
    public SeriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater myInfalter=LayoutInflater.from(parent.getContext());// getLayoutn
        View myView=myInfalter.inflate(R.layout.movieitem,parent,false);

        SeriesHolder holder=new SeriesHolder(myView);
        MobileAds.initialize(context,context.getResources().getString(R.string.admob_id));
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(context.getResources().getString(R.string.interstital_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesHolder holder, final int position) {
        Glide.with(context)
                .load(seriesModels.get(position).imageLink)
                .into(holder.imgView);
        holder.imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                    mInterstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdLoaded() {
                            // Code to be executed when an ad finishes loading.
                        }

                        @Override
                        public void onAdFailedToLoad(int errorCode) {
                            // Code to be executed when an ad request fails.
                            SeriesDetail.model=seriesModels.get(position);
                            Intent myIntent=new Intent(context,SeriesDetail.class);
                            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(myIntent);
                        }

                        @Override
                        public void onAdOpened() {
                            // Code to be executed when the ad is displayed.
                        }

                        @Override
                        public void onAdClicked() {
                            // Code to be executed when the user clicks on an ad.
                            SeriesDetail.model=seriesModels.get(position);
                            Intent myIntent=new Intent(context,SeriesDetail.class);
                            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(myIntent);
                        }

                        @Override
                        public void onAdLeftApplication() {
                            // Code to be executed when the user has left the app.
                        }

                        @Override
                        public void onAdClosed() {
                            // Code to be executed when the interstitial ad is closed.
                            SeriesDetail.model=seriesModels.get(position);
                            Intent myIntent=new Intent(context,SeriesDetail.class);
                            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(myIntent);
                        }
                    });
                }
                else {
                    SeriesDetail.model=seriesModels.get(position);
                    Intent myIntent=new Intent(context,SeriesDetail.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(myIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return seriesModels.size();
    }

    public class SeriesHolder extends RecyclerView.ViewHolder{
        ImageView imgView;
        public SeriesHolder(@NonNull View itemView) {
            super(itemView);
            imgView=itemView.findViewById(R.id.movieImage);
        }
    }
}
