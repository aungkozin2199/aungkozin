package com.akz.clientapk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.StrictMode;

import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        StrictMode.ThreadPolicy mypolicy=new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy(mypolicy);
        //
        TabLayout tabBar=findViewById(R.id.tabbar);
        ViewPager pager=findViewById(R.id.viewpager);
        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MovieFragment(),"Movies");
        adapter.addFragment(new SeriesFragment(),"Series");
        pager.setAdapter(adapter);
        tabBar.setupWithViewPager(pager);
    }
}
