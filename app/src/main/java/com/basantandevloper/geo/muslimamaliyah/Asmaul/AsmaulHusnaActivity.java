package com.basantandevloper.geo.muslimamaliyah.Asmaul;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import com.basantandevloper.geo.muslimamaliyah.R;
import com.basantandevloper.geo.muslimamaliyah.helper.LoadJsonAsset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AsmaulHusnaActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private AsmaulHusnaAdapter adapter;
    private ArrayList<AsmaulHusnaModel> asmaulHusnaModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = AsmaulHusnaActivity.this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(AsmaulHusnaActivity.this,R.color.colorBlue));
        setContentView(R.layout.activity_asmaul_husna);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadData();

        recyclerView.setNestedScrollingEnabled(false);
        adapter = new AsmaulHusnaAdapter(asmaulHusnaModels);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AsmaulHusnaActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }
    void loadData(){
        asmaulHusnaModels = new ArrayList<>();
        LoadJsonAsset jsonAsset = new LoadJsonAsset();
        String jsonResponse = jsonAsset.loadJSONFromAsset(AsmaulHusnaActivity.this, "asmaulhusna.json");
        AsmaulHusnaModel[] surahJsonResponse = new AsmaulJsonResponse().parseJSON(jsonResponse);
        List<AsmaulHusnaModel> listAsma = Arrays.asList(surahJsonResponse);
        for (AsmaulHusnaModel sm : listAsma) {
            asmaulHusnaModels.add(new AsmaulHusnaModel(sm.getId(), sm.getNama(),
                    sm.getArti(), sm.getArab()));
        }
    }
}
