package com.basantandevloper.geo.muslimamaliyah.Quran;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.basantandevloper.geo.muslimamaliyah.Database.DatabaseHelper;
import com.basantandevloper.geo.muslimamaliyah.R;
import com.basantandevloper.geo.muslimamaliyah.SettingsActivity;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LastAyatActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar ;
    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.tx_title)
    TextView tTitle;
    @BindView(R.id.tx_subtitle)
    TextView tSubTitle;
    @BindView(R.id.imgSetting)
    ImageView imgSetting;
    @BindView(R.id.ic_back)
    ImageView imgBack;
    SharedPreferences sharedPreferences;
    String quranFavorit ="quranFavorit";
    static final String KeyFav = "favKey";
    String prefFav;
    private SurahModel surahModel;
    private DatabaseHelper databaseHelper;
    private static final String BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout";
    private AyatModel model;
    private List<AyatResult> results;

    private ArrayList<SurahModel> surahArrayList;


    private ArrayList<AyatModel> ayatModels;
    AyatAdapter adapter;

    String typ = "umum";

    boolean isMore = true;
    boolean isLoading = false;

    String nomor,asma,nama,jumAyat,start,ayat,type,urut,rukuk,arti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_last_ayat);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();

        databaseHelper = new DatabaseHelper(LastAyatActivity.this);

        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LastAyatActivity.this,SettingsActivity.class);
                startActivity(intent);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void initViews() {
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);


        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd !=null) {

            nomor = (String) bd.get("nosurah");
            asma = (String)bd.get("asma");
            arti = (String)bd.get("arti");
            nama = (String) bd.get("surah");
            ayat = (String) bd.get("ayat");
            jumAyat = (String)bd.get("jumayat");

            Log.d("AYATAPA", "onCreate: "+nama+" ("+asma+")");
            getSupportActionBar().setTitle(nama+" ("+asma+")");
            tTitle.setText(nama+" ("+asma+")");
            tSubTitle.setText(arti+ "("+jumAyat+" ayat)");


            ayatModels =  new ArrayList<>();
            adapter = new AyatAdapter(ayatModels,LastAyatActivity.this,nama,nomor);
            mRecyclerView.setAdapter(adapter);

            databaseHelper = new DatabaseHelper(LastAyatActivity.this);

            getDataLastSQL(nomor,typ,Integer.valueOf(jumAyat));

            mRecyclerView.scrollToPosition(Integer.valueOf(ayat));
        }
    }


    private void getDataLastSQL(String nomor, String jenis, int limit) {
        ayatModels.clear();
        ayatModels.addAll(databaseHelper.getAyat(nomor,jenis,limit));
        adapter.notifyDataSetChanged();
    }


}
