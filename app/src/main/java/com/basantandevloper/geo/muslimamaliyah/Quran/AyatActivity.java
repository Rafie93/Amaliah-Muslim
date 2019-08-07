package com.basantandevloper.geo.muslimamaliyah.Quran;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.basantandevloper.geo.muslimamaliyah.API.APIClient;
import com.basantandevloper.geo.muslimamaliyah.API.ApiInterface;
import com.basantandevloper.geo.muslimamaliyah.Database.DatabaseHelper;
import com.basantandevloper.geo.muslimamaliyah.Qosidah.QosidahActivity;
import com.basantandevloper.geo.muslimamaliyah.R;
import com.basantandevloper.geo.muslimamaliyah.SettingsActivity;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AyatActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar ;
    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.favorite_button)
    MaterialFavoriteButton materialFavoriteButton;
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

    private ArrayList<AyatModel> ayatModels;
    AyatAdapter adapter;

    String typ = "umum";

    boolean isMore = false;
    boolean isLoading = false;

    String nomor,asma,nama,name,start,ayat,type,urut,rukuk,arti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ayat);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
        loadFavorit();
        initScrollListener();

        databaseHelper = new DatabaseHelper(AyatActivity.this);

        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AyatActivity.this,SettingsActivity.class);
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

    private void initScrollListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == ayatModels.size() - 1) {
                        //bottom of list!
                        if (Integer.valueOf(ayat) >= 50){
                            isMore = true;
                        }
                        if (isMore){
                            loadMore();
                        }
                        isLoading = true;
                    }
                    int posR = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                    SharedPreferences settingJ =  getSharedPreferences("LastRead",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settingJ.edit();
                    editor.putString("KeyLastNoSurah",nomor);
                    editor.putString("KeyLastSurah",nama);
                    editor.putString("KeyLastArti",arti);
                    editor.putString("KeyLastAsma",asma);
                    editor.putString("KeyJumlahAyat",ayat);
                    editor.putString("KeyLastAyat",String.valueOf(posR));
                    editor.putBoolean("KeyLastRead",true);
                    editor.apply();
                    editor.commit();

                }
            }
        });
    }

    private void loadMore() {
        ayatModels.add(null);
        adapter.notifyItemInserted(ayatModels.size() - 1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ayatModels.remove(ayatModels.size() - 1);
                int scrollPosition = ayatModels.size();
                adapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + 50;
                if (nextLimit >= Integer.valueOf(ayat)){
                    nextLimit = Integer.valueOf(ayat);
                }

                getDataSQL(nomor,typ,nextLimit);

                while (currentSize - 1 < nextLimit) {
                    currentSize++;
                }
                adapter.notifyDataSetChanged();
                isLoading = false;

            }
        }, 500);
    }

    private void initViews() {
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);


        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd !=null) {
            nomor = (String) bd.get("nomor");
            asma = (String)bd.get("asma");
            nama = (String) bd.get("nama");
            arti = (String) bd.get("arti");
            ayat = (String) bd.get("ayat");
            name = (String) bd.get("name");
            start = (String)bd.get("start");
            type = (String) bd.get("type");
            urut = (String) bd.get("urut");
            rukuk = (String) bd.get("rukuk");
            Log.d("AYATAPA", "onCreate: "+nama+" ("+asma+")");
            getSupportActionBar().setTitle(nama+" ("+asma+")");
            toolbar.setSubtitle(arti+ "("+ayat+" ayat)");
            tTitle.setText(nama+" ("+asma+")");
            tSubTitle.setText(arti+ "("+ayat+" ayat)");

            ayatModels =  new ArrayList<>();
            adapter = new AyatAdapter(ayatModels,AyatActivity.this,nama,nomor);
            mRecyclerView.setAdapter(adapter);

            databaseHelper = new DatabaseHelper(AyatActivity.this);
            getDataSQL(nomor,typ,50);
            if (ayatModels.size() <= 0){
                getJsonParse(nomor);
                if (ayatModels.size()<=0){
                    getDataFromServer(nomor);
                }
            }
        }
    }

    private void getJsonParse(final String nomor){
        final ProgressDialog progress;
        progress=new ProgressDialog(AyatActivity.this);
        progress.setMessage("Memuat Data Surah Ini");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();

        String fileNama = nomor+".json";
        //
        try {
            //Load File
            BufferedReader jsonReader = new BufferedReader(new InputStreamReader(getAssets().open(fileNama)));
            StringBuilder jsonBuilder = new StringBuilder();
            for (String line = null; (line = jsonReader.readLine()) != null;) {
                jsonBuilder.append(line).append("\n");
            }
            JSONTokener tokener = new JSONTokener(jsonBuilder.toString());
            JSONArray jsonArray = new JSONArray(tokener);

            for (int index = 0; index <  jsonArray.length(); index++) {

                JSONObject jsonObject = jsonArray.getJSONObject(index);

                String no = jsonObject.getString("nomor");
                String ar = jsonObject.getString("ar");
                String id = jsonObject.getString("id");
                String tr = jsonObject.getString("tr");
                saveToSqlit(nomor,typ,ar,id,no,tr);

                ayatModels.add(new AyatModel(nomor,typ,ar,id,no,tr));

            }
            adapter = new AyatAdapter(ayatModels,AyatActivity.this,nama,nomor);
            mRecyclerView.setAdapter(adapter);
            progress.dismiss();

        } catch (FileNotFoundException e) {
            Log.e("jsonFile", "file not found");
        } catch (IOException e) {
            Log.e("jsonFile", "ioerror");
        } catch (JSONException e) {
            Log.e("jsonFile", "error while parsing json");
        }
    }


    private void getDataFromServer(final String nomor) {
        ayatModels.clear();

        ApiInterface apiInterface = APIClient.getClientQuran().create(ApiInterface.class);
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(AyatActivity.this);
        //  progressDoalog.setMax(10);
        progressDoalog.setMessage("Mendownload Data pastikan koneksi Anda Lancar");
        progressDoalog.setTitle("Mohon Tunggu....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        //

        Call call = apiInterface.getAyat(nomor);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()){
                    results = (List<AyatResult>)response.body();
                    Log.d("RESPONSE", "onResponse: "+results.size());
                    for (int i=0; i < results.size(); i++){
                        String no = results.get(i).getNomor();
                        String ar = results.get(i).getAr();
                        String id = results.get(i).getId();
                        String tr = results.get(i).getTr();

//                        Log.d("AYAT"+no, "onResponse: "+ar+","+id+","+tr);

                        ayatModels.add(new AyatModel(nomor,typ,ar,id,no,tr));

                        saveToSqlit(nomor,typ,ar,id,no,tr);
                    }
                    adapter = new AyatAdapter(ayatModels,AyatActivity.this,nama,nomor);
                    mRecyclerView.setAdapter(adapter);
                    progressDoalog.dismiss();

                }else{
                    progressDoalog.dismiss();
                    Toast.makeText(AyatActivity.this,"Gagal Memuat Data",Toast.LENGTH_SHORT).show();
                    Log.d("RESPONSE", "GAGAL");
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(AyatActivity.this,"Cek Koneksi Internet Anda Untuk Mendownload Data Ini",Toast.LENGTH_SHORT).show();
                progressDoalog.dismiss();

            }
        });


    }

    private void saveToSqlit(String surah, String typ, String ar, String id, String no, String tr) {
        model = new AyatModel(surah,typ,ar,id,no,tr);
        model.setSurah(surah);
        model.setType(typ);
        model.setAr(ar);
        model.setId(id);
        model.setNomor(no);
        model.setTr(tr);
        databaseHelper.addAyat(model);
    }

    private void getDataSQL(String nomor, String jenis, int limit) {
        ayatModels.clear();
        ayatModels.addAll(databaseHelper.getAyat(nomor,jenis,limit));
        adapter.notifyDataSetChanged();
    }

    private void simpan_favorit() {
        Log.d("FAVORIT", "simpan_favorit: "+nomor);
        surahModel = new SurahModel(nomor,nama,asma,name,start,ayat,type,urut,rukuk,arti);
        surahModel.setNomor(nomor);
        surahModel.setNama(nama);
        surahModel.setAsma(asma);
        surahModel.setName(name);
        surahModel.setStart(start);
        surahModel.setAyat(ayat);
        surahModel.setType(type);
        surahModel.setUrut(urut);
        surahModel.setRukuk(rukuk);
        surahModel.setArti(arti);
        databaseHelper.addFavQuran(surahModel);
    }
    void simpanFavorit (){
        quranFavorit = "quranFavorit_"+nomor;
        SharedPreferences setting=  this.getSharedPreferences(quranFavorit,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(KeyFav, String.valueOf(1));
        editor.apply();
        editor.commit();
    }

    void loadFavorit(){
        quranFavorit = "quranFavorit_"+nomor;
        SharedPreferences share = getSharedPreferences(quranFavorit,Context.MODE_PRIVATE);
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
                                Snackbar.make(buttonView, "Ditambahkan sebagai Surah Favorit",
                                        Snackbar.LENGTH_SHORT).show();
                            } else {
                                resetFavorit();
                                databaseHelper = new DatabaseHelper(AyatActivity.this);
                                databaseHelper.deleteFavQuran(nomor);
                                Snackbar.make(buttonView, "Dihapus sebagai Surah Favorit",
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
                                Snackbar.make(buttonView, "Ditambahkan sebagai Surah Favorit",
                                        Snackbar.LENGTH_SHORT).show();
                            } else {
                                resetFavorit();
                                Intent intent = getIntent();
                                Bundle bd = intent.getExtras();
                                String  id= (String) bd.get("nomor");
                                databaseHelper = new DatabaseHelper(AyatActivity.this);
                                databaseHelper.deleteFavQuran(nomor);
                                Snackbar.make(buttonView, "Dihapus sebagai Qosidah Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }
    void resetFavorit(){
        quranFavorit = "myFavorit_"+nomor;
        SharedPreferences setting=  this.getSharedPreferences(quranFavorit,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(KeyFav, String.valueOf(0));
        editor.apply();
        editor.commit();
        loadFavorit();
    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, mRecyclerView.getLayoutManager().onSaveInstanceState());
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        if(savedInstanceState != null)
//        {
//            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
//            mRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
//        }
//    }


}
