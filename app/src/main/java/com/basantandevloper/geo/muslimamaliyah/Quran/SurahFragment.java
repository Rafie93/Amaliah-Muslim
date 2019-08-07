package com.basantandevloper.geo.muslimamaliyah.Quran;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.basantandevloper.geo.muslimamaliyah.API.APIClient;
import com.basantandevloper.geo.muslimamaliyah.API.ApiInterface;
import com.basantandevloper.geo.muslimamaliyah.Database.DatabaseHelper;
import com.basantandevloper.geo.muslimamaliyah.R;
import com.basantandevloper.geo.muslimamaliyah.helper.LoadJsonAsset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SurahFragment extends Fragment {
    private View view;
    private DatabaseHelper databaseHelper;

    private RecyclerView recyclerView;
    private SurahAdapter adapter;
    private ArrayList<SurahModel> surahArrayList;
    private SurahModel surah;

    private AyatModel model;
    private List<AyatResult> results;
    private ArrayList<AyatModel> ayatModels;

    boolean isMore = true;
    boolean isLoading = false;
    boolean isLast = false;
    final String typ = "umum";
    ProgressDialog progressDoalog;
    String lastSurah,lastAyat,lastAsma,lastNomor,lastArti,lastJumAyat;
    TextView txtSurah,txtAyat,txtAsma;
    private LinearLayout linearLayout;

    public SurahFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_surah, container, false);
        setHasOptionsMenu(true);

        linearLayout = (LinearLayout)view.findViewById(R.id.lin_last);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        txtSurah = (TextView)view.findViewById(R.id.rowLastSurah) ;
        txtAyat = (TextView)view.findViewById(R.id.rowLastAyat);
        txtAsma = (TextView)view.findViewById(R.id.rowAsma);
       // recyclerView.setNestedScrollingEnabled(false);

        surahArrayList =  new ArrayList<>();
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        databaseHelper = new DatabaseHelper(getActivity());
        getDataSQLite(57);
        if (surahArrayList.size()<=0){
            progressDoalog = new ProgressDialog(getActivity());
            //  progressDoalog.setMax(10);
            progressDoalog.setMessage("Memuat Data Mohon Tunggu....");
            progressDoalog.setTitle("Loading....");
            progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDoalog.show();

            surahParsedResponse();
        }

        getLastRead();
        initAdapter();
        initScrollListener();

        return view;
    }

    private void getLastRead() {
        SharedPreferences share = getActivity().getSharedPreferences("LastRead",Context.MODE_PRIVATE);
        isLast = share.getBoolean("KeyLastRead",false);
        lastNomor = share.getString("KeyLastNoSurah","0");
        lastSurah = share.getString("KeyLastSurah","0");
        lastArti = share.getString("KeyLastArti","0");
        lastAsma = share.getString("KeyLastAsma","0");
        lastJumAyat = share.getString("KeyJumlahAyat","0");
        lastAyat = share.getString("KeyLastAyat","0");
        if (isLast){
            txtSurah.setText(lastSurah);
            txtAsma.setText(lastAsma);
            txtAyat.setText(lastAyat);
            linearLayout.setVisibility(View.VISIBLE);
        }else{
            txtSurah.setText("");
            txtAsma.setText("");
            txtAyat.setText("");
            linearLayout.setVisibility(View.GONE);
        }

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),LastAyatActivity.class);
                intent.putExtra("nosurah",lastNomor);
                intent.putExtra("arti",lastArti);
                intent.putExtra("jumayat",lastJumAyat);
                intent.putExtra("surah",lastSurah);
                intent.putExtra("asma",lastAsma);
                intent.putExtra("ayat",lastAyat);
                getActivity().startActivity(intent);
            }
        });

    }

    private void initAdapter(){
        adapter = new SurahAdapter(surahArrayList);
        recyclerView.setAdapter(adapter);
    }
    private void surahParsedResponse() {
        surahArrayList = new ArrayList<>();
        LoadJsonAsset jsonAsset = new LoadJsonAsset();
        String jsonResponse = jsonAsset.loadJSONFromAsset(getContext(), "quran_list.json");
        SurahModel[] surahJsonResponse = new SurahJsonResponse().parseJSON(jsonResponse);
        List<SurahModel> listSurah = Arrays.asList(surahJsonResponse);

          deletetoSqlite();
        for (SurahModel sm : listSurah) {
            surahArrayList.add(new SurahModel(sm.getNomor(), sm.getNama(),
                    sm.getAsma(), sm.getName(), sm.getStart(), sm.getAyat(), sm.getType(),
                    sm.getUrut(), sm.getRukuk(), sm.getArti()));

            savetoSQLite(sm.getNomor(), sm.getNama(),
                    sm.getAsma(), sm.getName(), sm.getStart(), sm.getAyat(), sm.getType(),
                    sm.getUrut(), sm.getRukuk(), sm.getArti());

        }

        progressDoalog.dismiss();
    }

    private void getDataSQLite(Integer limit){
        surahArrayList.clear();
        surahArrayList.addAll(databaseHelper.getAllSurah(limit));
    }

    private void deletetoSqlite(){
        databaseHelper = new DatabaseHelper(getActivity());
        databaseHelper.deleteSurah();
    }
    private void getFilterData(String cari){
        surahArrayList.clear();
        isMore = false;
        surahArrayList.addAll(databaseHelper.getCariSurah(cari));
    }
    private void savetoSQLite(String val1,String val2, String val3, String val4, String val5,String val6,String val7, String val8, String val9, String val10){
        surah = new SurahModel(val1,val2,val3,val4,val5,val6,val7,val8,val9,val10);
        surah.setNomor(val1);
        surah.setNama(val2);
        surah.setAsma(val3);
        surah.setName(val4);
        surah.setStart(val5);
        surah.setAyat(val6);
        surah.setType(val7);
        surah.setUrut(val8);
        surah.setRukuk(val9);
        surah.setArti(val10);

        databaseHelper.addSurah(surah);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       // super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getFilterData(newText);
                adapter = new SurahAdapter(surahArrayList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);

                return false;
            }
        });
    }

    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == surahArrayList.size() - 1) {
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
        surahArrayList.add(null);
        adapter.notifyItemInserted(surahArrayList.size() - 1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                surahArrayList.remove(surahArrayList.size() - 1);
                int scrollPosition = surahArrayList.size();
                adapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + 57;
                if (nextLimit >= 114){
                    nextLimit = 114;
                }

                getDataSQLite(nextLimit);

                while (currentSize - 1 < nextLimit) {
                    currentSize++;
                }
                adapter.notifyDataSetChanged();
                isLoading = false;

            }
        }, 500);
    }



}
