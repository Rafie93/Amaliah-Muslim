package com.basantandevloper.geo.muslimamaliyah.Quran;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.basantandevloper.geo.muslimamaliyah.MainActivity;
import com.basantandevloper.geo.muslimamaliyah.R;
import com.basantandevloper.geo.muslimamaliyah.helper.TabLayoutAdd;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class QuranActivity extends AppCompatActivity {
    private TextView textView;
    private ViewPager mViewPager;
    private TabLayout mTableLayout;
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran);

        mViewPager = (ViewPager)findViewById(R.id.viewpager);
        mTableLayout = (TabLayout )findViewById(R.id.tabs);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MobileAds.initialize(this, "ca-app-pub-5914624441048150~1438549841");

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("cca-app-pub-5914624441048150/7101842009");
        interstitialAd.loadAd(new AdRequest.Builder().addKeyword("quran").addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());

        interstitialAd.setAdListener(new AdListener()
                                        {
                                            @Override
                                            public void onAdClosed() {
                                                Intent intent = new Intent(QuranActivity.this,MainActivity.class);
                                                startActivity(intent);
                                                interstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
                                            }
                                        });


        setupViewPager(mViewPager);
        mTableLayout.setupWithViewPager(mViewPager);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if (interstitialAd.isLoaded()){
                    interstitialAd.show();
                 }else {
                    onBackPressed();
                }
                break;
        }
        return true;
    }

    private void setupViewPager(ViewPager viewPager){
        TabLayoutAdd adapter = new TabLayoutAdd(getSupportFragmentManager());
        adapter.addFragment(new SurahFragment(),"SURAH");
        adapter.addFragment(new JuzFragment(),"JUZ");
        adapter.addFragment(new PenandaQuranFragment(),"PENANDA");
        viewPager.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
