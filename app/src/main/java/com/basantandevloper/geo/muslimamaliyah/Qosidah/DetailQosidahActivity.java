package com.basantandevloper.geo.muslimamaliyah.Qosidah;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.basantandevloper.geo.muslimamaliyah.API.APIClient;
import com.basantandevloper.geo.muslimamaliyah.API.ApiInterface;
import com.basantandevloper.geo.muslimamaliyah.Database.DatabaseHelper;
import com.basantandevloper.geo.muslimamaliyah.R;
import com.basantandevloper.geo.muslimamaliyah.utils.Constant;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailQosidahActivity extends AppCompatActivity {
    @BindView(R.id.recycler_view_qasidah)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.layoutKosong)
    LinearLayout layout;
    @BindView(R.id.tx_title)
    TextView txtTitle;
    String id_qosidah,nama;
    private DatabaseHelper databaseHelper;
    private QosidahModel qosidahModel;
    private ArrayList<IsiQasidah> isiQasidah;
    DetailQosidahAdapter qosidahAdapter;

    SharedPreferences sharedPreferences;
    String myFavorit ="myFavorit";
    static final String KeyFav = "favKey";
    String prefFav;
    @BindView(R.id.favorite_button)
    MaterialFavoriteButton materialFavoriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detail_qosidah);
        ButterKnife.bind(this);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        layout.setVisibility(View.GONE);
        initViews();
        loadFavorit();
        databaseHelper = new DatabaseHelper(DetailQosidahActivity.this);

    }


    private void simpan_favorit() {
        Log.d("FAVORIT", "simpan_favorit: "+nama);
        qosidahModel = new QosidahModel(id_qosidah,nama);
        qosidahModel.setId(id_qosidah);
        qosidahModel.setJudul(nama);
        databaseHelper.addFavQosidah(qosidahModel);
    }

    private void initViews() {
        isiQasidah =  new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(DetailQosidahActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        qosidahAdapter = new DetailQosidahAdapter(isiQasidah, DetailQosidahActivity.this);
        recyclerView.setAdapter(qosidahAdapter);
//        qosidahAdapter.notifyDataSetChanged();

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd !=null) {
             id_qosidah  = (String) bd.get("id");
             nama  = (String) bd.get("nama");
             txtTitle.setText(nama);
            getSupportActionBar().setTitle(nama);
            toolbar.setTitle(nama);
            databaseHelper = new DatabaseHelper(DetailQosidahActivity.this);
            getDataSQL(id_qosidah);
            if (cekData() <= 0){
                Toast.makeText(DetailQosidahActivity.this,"Terjadi Kesalahan dalam memuat data atau data tidak ada",Toast.LENGTH_SHORT).show();
            }
        }

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private int cekData(){
        return isiQasidah.size();
    }

    void getDataSQL(String id){
        isiQasidah.clear();
        isiQasidah.addAll(databaseHelper.getIsiQasidah(id));
        qosidahAdapter.notifyDataSetChanged();
    }


    void simpanFavorit (){
        myFavorit = "myFavorit_"+id_qosidah;
        SharedPreferences setting=  this.getSharedPreferences(myFavorit,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(KeyFav, String.valueOf(1));
        editor.apply();
        editor.commit();
    }
    void loadFavorit(){
        myFavorit = "myFavorit_"+id_qosidah;
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
                                databaseHelper = new DatabaseHelper(DetailQosidahActivity.this);
                                databaseHelper.deleteFavQosidah(id_qosidah);
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
                                databaseHelper = new DatabaseHelper(DetailQosidahActivity.this);
                                databaseHelper.deleteFavQosidah(id);
                                Snackbar.make(buttonView, "Dihapus sebagai Qosidah Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    void resetFavorit(){
        myFavorit = "myFavorit_"+id_qosidah;
        SharedPreferences setting=  this.getSharedPreferences(myFavorit,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(KeyFav, String.valueOf(0));
        editor.apply();
        editor.commit();
        loadFavorit();
    }
}
