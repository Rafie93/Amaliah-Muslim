package com.basantandevloper.geo.muslimamaliyah.Quran;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;

import com.basantandevloper.geo.muslimamaliyah.API.APIClient;
import com.basantandevloper.geo.muslimamaliyah.API.ApiInterface;
import com.basantandevloper.geo.muslimamaliyah.Database.DatabaseHelper;
import com.basantandevloper.geo.muslimamaliyah.R;
import com.basantandevloper.geo.muslimamaliyah.SettingsActivity;

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

public class JuzActivity extends AppCompatActivity {
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

    private DatabaseHelper databaseHelper;

    private AyatModel model;
    private List<AyatResult> results;

    private ArrayList<AyatModel> ayatModels;
    AyatAdapter adapter;

    String typ = "umum";

    boolean isMore = true;
    boolean isLoading = false;

    int total=0;


    String juz,mulaiSurah,mulaiAyat,mulaiName,endSurah,endAyat,endName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_juz);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JuzActivity.this,SettingsActivity.class);
                startActivity(intent);
            }
        });

        initView();
        initScrollListener();

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
                        if (isMore){
                            loadMore();
                        }
                        isLoading = true;
                    }
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
                if (nextLimit >= total){
                    nextLimit = total;
                }

                getDataSQL(typ,mulaiSurah,mulaiAyat,endSurah,endAyat,nextLimit);


                while (currentSize - 1 < nextLimit) {
                    currentSize++;
                }
                adapter.notifyDataSetChanged();
                isLoading = false;

            }
        }, 1000);
    }


    private void initView() {
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);

        ayatModels =  new ArrayList<>();
        adapter = new AyatAdapter(ayatModels,JuzActivity.this,"0","0");
        mRecyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd !=null) {
            juz = (String) bd.get("juz");
            mulaiSurah = (String) bd.get("mulaiSurah");
            mulaiName = (String) bd.get("mulaiName");
            mulaiAyat = (String) bd.get("mulaiAyat");
            endSurah = (String) bd.get("endSurah");
            endAyat = (String) bd.get("endAyat");
            endName = (String) bd.get("endName");
            tTitle.setText("Juz " + juz);
            tSubTitle.setText(mulaiName + ":" + mulaiAyat + "-" + endName + ":" + endAyat);

            databaseHelper = new DatabaseHelper(JuzActivity.this);
            Integer count = databaseHelper.getJumlahAyatJuz(typ,mulaiSurah,mulaiAyat,endSurah,endAyat);
            total = count;
            getDataSQL(typ,mulaiSurah,mulaiAyat,endSurah,endAyat,50);

            if (ayatModels.size() <= 0){
                getJsonParse(mulaiSurah,endSurah);
                if (ayatModels.size() <= 0){
                    getDataFromServer(mulaiSurah,endSurah);
                }
            }
        }
    }

    private void getJsonParse(String mulaiSurah, String endSurah){
        final ProgressDialog progress;
        progress=new ProgressDialog(JuzActivity.this);
        progress.setMessage("Memuat Data Juz Ini");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();

        Integer mulai = Integer.valueOf(mulaiSurah);
        Integer sampai = Integer.valueOf(endSurah);

        for (int m = mulai; m <= sampai; m++){
            deleteAyatSurah(String.valueOf(m));
            String fileNama = m+".json";
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
                    saveToSqlit(String.valueOf(m),typ,ar,id,no,tr);
                    ayatModels.add(new AyatModel(String.valueOf(m),typ,ar,id,no,tr));

                }
            } catch (FileNotFoundException e) {
                Log.e("jsonFile", "file not found");
            } catch (IOException e) {
                Log.e("jsonFile", "ioerror");
            } catch (JSONException e) {
                Log.e("jsonFile", "error while parsing json");
            }

        }
        //
        Integer count = databaseHelper.getJumlahAyatJuz(typ,mulaiSurah,mulaiAyat,endSurah,endAyat);
        total = count;
        getDataSQL(typ,mulaiSurah,mulaiAyat,endSurah,endAyat,50);

        progress.dismiss();

    }


    private void getDataFromServer(String mulaiSurah, String endSurah) {
        ayatModels.clear();
        ApiInterface apiInterface = APIClient.getClientQuran().create(ApiInterface.class);
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(JuzActivity.this);
        //  progressDoalog.setMax(10);
        progressDoalog.setMessage("Mendownload Data Dari Server....");
        progressDoalog.setTitle("Loading....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        //
        Integer mulai = Integer.valueOf(mulaiSurah);
        Integer sampai = Integer.valueOf(endSurah);

        for (int m = mulai; m <= sampai; m++){
            Call call = apiInterface.getAyat(String.valueOf(m));
            final int finalM = m;
            deleteAyatSurah(String.valueOf(m));

            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if (response.isSuccessful()){
                        results = (List<AyatResult>)response.body();
                        for (int i=0; i < results.size(); i++){
                            String no = results.get(i).getNomor();
                            String ar = results.get(i).getAr();
                            String id = results.get(i).getId();
                            String tr = results.get(i).getTr();
                            ayatModels.add(new AyatModel(String.valueOf(finalM),typ,ar,id,no,tr));
                            saveToSqlit(String.valueOf(finalM),typ,ar,id,no,tr);
                        }
                    }else{
                        progressDoalog.dismiss();
                        Toast.makeText(JuzActivity.this,"Gagal Memuat Data",Toast.LENGTH_SHORT).show();
                        Log.d("RESPONSE", "GAGAL");
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Toast.makeText(JuzActivity.this,"Cek Koneksi Internet Anda Untuk Mendownload Data Ini",Toast.LENGTH_SHORT).show();
                    progressDoalog.dismiss();

                }
            });

        }
        Integer count = databaseHelper.getJumlahAyatJuz(typ,mulaiSurah,mulaiAyat,endSurah,endAyat);
        total = count;
        getDataSQL(typ,mulaiSurah,mulaiAyat,endSurah,endAyat,50);

        progressDoalog.dismiss();

    }

    private void saveToSqlit(String nomorSurah, String typ, String ar, String id, String no, String tr) {
        model = new AyatModel(nomorSurah,typ,ar,id,no,tr);
        model.setSurah(nomorSurah);
        model.setType(typ);
        model.setAr(ar);
        model.setId(id);
        model.setNomor(no);
        model.setTr(tr);
        databaseHelper.addAyat(model);
    }

    private void getDataSQL(String jenis, String mulaiSurah, String mulaiAyat, String endSurah, String endAyat, int limit) {
        ayatModels.clear();
        ayatModels.addAll(databaseHelper.getAyatJuz(jenis,mulaiSurah,mulaiAyat,endSurah,endAyat,limit));
        adapter.notifyDataSetChanged();
    }

    void deleteAyatSurah(String nomor){
        databaseHelper = new DatabaseHelper(JuzActivity.this);
        databaseHelper.deleteAyatSurah(typ,nomor);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
