package com.basantandevloper.geo.muslimamaliyah.Sholat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.basantandevloper.geo.muslimamaliyah.R;

public class DetailSholatActivity extends AppCompatActivity {
    TextView txt_judul,txt_isi;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sholat);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        txt_judul = (TextView)findViewById(R.id.tx_title);
        txt_isi = (TextView)findViewById(R.id.rowIsi);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd !=null) {
            String judul = (String) bd.get("judul");
            String isi = (String) bd.get("isi");
            txt_judul.setText(judul);
            txt_isi.setText(isi);

            getSupportActionBar().setTitle(judul);

        }
    }
}
