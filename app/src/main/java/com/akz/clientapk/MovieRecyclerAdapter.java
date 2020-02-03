package com.akz.clientapk;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

public class MovieRecyclerAdapter  extends RecyclerView.Adapter<MovieRecyclerAdapter.MoiveHolder> {
    private RewardedAd rewardedAd;
    ArrayList<MovieModel> movieModels=new ArrayList<>();
    Context context;
    Activity myActivity;

    public MovieRecyclerAdapter(ArrayList<MovieModel> movieModels, Context context, Activity myActivity) {
        this.movieModels = movieModels;
        this.context = context;
        this.myActivity = myActivity;
    }

    @NonNull
    @Override
    public MoiveHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater myInfalter=LayoutInflater.from(parent.getContext());// getLayoutn
        View myView=myInfalter.inflate(R.layout.movieitem,parent,false);
        MoiveHolder holder=new MoiveHolder(myView);
        //
        MobileAds.initialize(context,context.getResources().getString(R.string.admob_id));
        rewardedAd = new RewardedAd(context,
                context.getResources().getString(R.string.reward_id));
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                // Ad failed to load.
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MoiveHolder holder, final int position) {
        Glide.with(context)
                .load(movieModels.get(position).imageLink)
                .into(holder.imgView);
            holder.imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(rewardedAd.isLoaded())
                    {
                        RewardedAdCallback adCallback = new RewardedAdCallback() {
                            @Override
                            public void onRewardedAdOpened() {
                                // Ad opened.
                            }

                            @Override
                            public void onRewardedAdClosed() {

                            }

                            @Override
                            public void onUserEarnedReward(@NonNull RewardItem reward) {
                                try {
                                    VideoPlayActivity.videourl=MediaFireConnect.getVideoFileLink(movieModels.get(position).videoLink);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Intent intent=new Intent(context,VideoPlayActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                context.startActivity(intent);
                            }

                            @Override
                            public void onRewardedAdFailedToShow(int errorCode) {
                                try {
                                    VideoPlayActivity.videourl=MediaFireConnect.getVideoFileLink(movieModels.get(position).videoLink);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Intent intent=new Intent(context,VideoPlayActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                context.startActivity(intent);
                            }
                        };
                        rewardedAd.show(myActivity, adCallback);
                    }
                    else{
                        try {
                            VideoPlayActivity.videourl=MediaFireConnect.getVideoFileLink(movieModels.get(position).videoLink);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Intent intent=new Intent(context,VideoPlayActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }

            }});




}

    @Override
    public int getItemCount() {
        return movieModels.size();
    }

    public class MoiveHolder extends RecyclerView.ViewHolder{
        ImageView imgView;
        public MoiveHolder(@NonNull View itemView) {
            super(itemView);
            imgView=itemView.findViewById(R.id.movieImage);
        }
    }

}
