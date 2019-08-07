package com.basantandevloper.geo.muslimamaliyah.Sholat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.basantandevloper.geo.muslimamaliyah.MainActivity;
import com.basantandevloper.geo.muslimamaliyah.R;
import com.basantandevloper.geo.muslimamaliyah.helper.TabLayoutAdd;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class SholatActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout mTableLayout;
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = SholatActivity.this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(SholatActivity.this,R.color.colorPrimaryDark));
        setContentView(R.layout.activity_sholat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mViewPager = (ViewPager)findViewById(R.id.viewpager);
        mTableLayout = (TabLayout)findViewById(R.id.tabs);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MobileAds.initialize(this, "ca-app-pub-5914624441048150~1438549841");

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-5914624441048150/8863288882");
        interstitialAd.loadAd(new AdRequest.Builder().addKeyword("sholat").addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());

        interstitialAd.setAdListener(new AdListener()
        {
            @Override
            public void onAdClosed() {
                Intent intent = new Intent(SholatActivity.this,MainActivity.class);
                startActivity(intent);
                interstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
            }
        });

        setupViewPager(mViewPager);
        mTableLayout.setupWithViewPager(mViewPager);
    }
    private void setupViewPager(ViewPager viewPager){
        TabLayoutAdd adapter = new TabLayoutAdd(getSupportFragmentManager());
        adapter.addFragment(new SholatFragment(),"SHOLAT");
        adapter.addFragment(new WiridFragment(),"WIRID");
        viewPager.setAdapter(adapter);

    }

}
