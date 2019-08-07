package com.basantandevloper.geo.muslimamaliyah.Dzikir;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.basantandevloper.geo.muslimamaliyah.Database.DatabaseHelper;
import com.basantandevloper.geo.muslimamaliyah.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DzikirActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycler_dzikir)
    RecyclerView recyclerView;

    protected Cursor cursor;
    private DatabaseHelper databaseHelper;
    private AppCompatActivity activity = DzikirActivity.this;
    Context context = DzikirActivity.this;
    private ArrayList<DzikirModel> dzikirList;
    private DzikirAdapter dzikirRecyclerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = DzikirActivity.this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(DzikirActivity.this,R.color.deeppurple));
        setContentView(R.layout.activity_tasbih);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView.setNestedScrollingEnabled(false);

        initObjects();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent intent = new Intent(DzikirActivity.this,TambahDzikirActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initObjects() {
        dzikirList = new ArrayList<>();
        dzikirRecyclerAdapter = new DzikirAdapter(dzikirList, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(dzikirRecyclerAdapter);
        databaseHelper = new DatabaseHelper(activity);

        getDataFromSQLite();
    }
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                dzikirList.clear();
                dzikirList.addAll(databaseHelper. getAllDzikir());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                dzikirRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
    

}
