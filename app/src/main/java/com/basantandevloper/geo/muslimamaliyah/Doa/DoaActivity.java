package com.basantandevloper.geo.muslimamaliyah.Doa;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import com.basantandevloper.geo.muslimamaliyah.R;
import com.basantandevloper.geo.muslimamaliyah.helper.TabLayoutAdd;

public class DoaActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout mTableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = DoaActivity.this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(DoaActivity.this,R.color.deeppurple));
        setContentView(R.layout.activity_doa);
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
        adapter.addFragment(new DoaFragment(),"DOA");
        adapter.addFragment(new FavoritDoaFragment(),"FAVORIT");
        viewPager.setAdapter(adapter);

    }

}
