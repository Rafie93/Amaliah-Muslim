package com.basantandevloper.geo.muslimamaliyah.Qosidah;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.basantandevloper.geo.muslimamaliyah.Database.DatabaseHelper;
import com.basantandevloper.geo.muslimamaliyah.MainActivity;
import com.basantandevloper.geo.muslimamaliyah.R;
import com.basantandevloper.geo.muslimamaliyah.helper.TabLayoutAdd;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class QosidahActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout mTableLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qosidah);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mViewPager = (ViewPager)findViewById(R.id.viewpager);
        mTableLayout = (TabLayout)findViewById(R.id.tabs);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupViewPager(mViewPager);
        mTableLayout.setupWithViewPager(mViewPager);

    }
    private void setupViewPager(ViewPager viewPager){
        TabLayoutAdd adapter = new TabLayoutAdd(getSupportFragmentManager());
        adapter.addFragment(new QosidahFragment(),"QOSIDAH");
        adapter.addFragment(new FavoritQosidahFragment(),"FAVORIT");
        viewPager.setAdapter(adapter);

    }

}
