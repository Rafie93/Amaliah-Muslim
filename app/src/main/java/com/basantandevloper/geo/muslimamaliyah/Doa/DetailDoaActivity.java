package com.basantandevloper.geo.muslimamaliyah.Doa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.basantandevloper.geo.muslimamaliyah.Database.DatabaseHelper;
import com.basantandevloper.geo.muslimamaliyah.Qosidah.DetailQosidahActivity;
import com.basantandevloper.geo.muslimamaliyah.R;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailDoaActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rowArabic)
    TextView txtArab;
    @BindView(R.id.rowLathin)
    TextView txtLathin;
    @BindView(R.id.rowTerjemahan)
    TextView txtArti;
    @BindView(R.id.tx_title)
    TextView txtTitle;
    @BindView(R.id.favorite_button)
    MaterialFavoriteButton materialFavoriteButton;

    private DatabaseHelper databaseHelper;
    private String id,judul,arab,lathin,arti;

    SharedPreferences sharedPreferences;
    String myFavorit ="myFavoritDoa";
    static final String KeyFav = "favDoaKey";
    String prefFav;

    private ListDoa listDoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = DetailDoaActivity.this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(DetailDoaActivity.this,R.color.deeppurple));
        setContentView(R.layout.activity_detail_doa);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(DetailDoaActivity.this);
        String prefSizeArab = preference.getString(DetailDoaActivity.this.getString(R.string.key_list_arabic_size),"0");
        Integer huSize = Integer.valueOf(prefSizeArab);
        Integer ukuruanArab = 22;
        if (huSize == 6){ ukuruanArab = 48;
        }else if  (huSize == 5){ ukuruanArab = 36;
        }else if (huSize == 4){ ukuruanArab = 28;
        }else if  (huSize == 3) { ukuruanArab = 22;
        }else if  (huSize == 2){ ukuruanArab =18;
        }else if  (huSize == 6){ ukuruanArab = 14;
        }else{ ukuruanArab = 22; }

        if (bd!=null){
            id = (String)bd.get("id");
            judul = (String)bd.get("judul");

             arab = (String)bd.get("isi_arab");
             txtArab.setTextSize(ukuruanArab);
             txtArab.setText(arab);

            lathin = (String)bd.get("latin");
            txtLathin.setText(lathin);

             arti = (String)bd.get("arti");
             txtArti.setText(arti);

             txtTitle.setText(judul);
             getSupportActionBar().setTitle(judul);

             loadFavorit();
        }
        databaseHelper = new DatabaseHelper(DetailDoaActivity.this);

    }
    void simpanFavorit (){
        myFavorit = "myFavoritDoa_"+id;
        SharedPreferences setting=  this.getSharedPreferences(myFavorit,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(KeyFav, String.valueOf(1));
        editor.apply();
        editor.commit();
    }

    void loadFavorit(){
        myFavorit = "myFavoritDoa_"+id;
        SharedPreferences share = getSharedPreferences(myFavorit,Context.MODE_PRIVATE);
        prefFav = share.getString(KeyFav,"");

        if (prefFav.equals("1")){
            materialFavoriteButton.setFavorite(true);
            materialFavoriteButton.setOnFavoriteChangeListener(
                    new MaterialFavoriteButton.OnFavoriteChangeListener() {
                        @Override
                        public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                            if (favorite == true) {
                                simpan_favorit();
                                simpanFavorit();
                                Snackbar.make(buttonView, "Ditambahkan sebagai Qosidah Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            } else {
                                resetFavorit();
                                databaseHelper = new DatabaseHelper(DetailDoaActivity.this);
                                databaseHelper.deleteFavDoa(id);
                                Snackbar.make(buttonView, "Dihapus sebagai Qosidah Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });

        }else{
            materialFavoriteButton.setOnFavoriteChangeListener(
                    new MaterialFavoriteButton.OnFavoriteChangeListener() {
                        @Override
                        public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                            if (favorite == true) {
                                simpan_favorit();
                                simpanFavorit();
                                Snackbar.make(buttonView, "Ditambahkan sebagai Qosidah Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            } else {
                                resetFavorit();
                                Intent intent = getIntent();
                                Bundle bd = intent.getExtras();
                                String  id= (String) bd.get("id");
                                databaseHelper = new DatabaseHelper(DetailDoaActivity.this);
                                databaseHelper.deleteFavDoa(id);
                                Snackbar.make(buttonView, "Dihapus sebagai Qosidah Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    void resetFavorit(){
        myFavorit = "myFavoritDoa_"+id;
        SharedPreferences setting=  this.getSharedPreferences(myFavorit,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(KeyFav, String.valueOf(0));
        editor.apply();
        editor.commit();
        loadFavorit();
    }

    private void simpan_favorit() {
//        Log.d("FAVORIT", "simpan_favorit: "+nama);
        listDoa = new ListDoa(Integer.valueOf(id),judul,arab,arti,lathin);
        listDoa.setId(Integer.valueOf(id));
        listDoa.setJudul(judul);
        listDoa.setIsiArab(arab);
        listDoa.setArti(arti);
        listDoa.setLatin(lathin);
        databaseHelper.addFavDoa(listDoa);
    }
}
