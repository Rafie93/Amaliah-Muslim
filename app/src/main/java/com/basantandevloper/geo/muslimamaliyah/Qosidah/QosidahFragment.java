package com.basantandevloper.geo.muslimamaliyah.Qosidah;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
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
import android.widget.TextView;

import com.basantandevloper.geo.muslimamaliyah.API.APIClient;
import com.basantandevloper.geo.muslimamaliyah.API.ApiInterface;
import com.basantandevloper.geo.muslimamaliyah.Database.DatabaseHelper;
import com.basantandevloper.geo.muslimamaliyah.MediaPlayer.SongActivity;
import com.basantandevloper.geo.muslimamaliyah.R;
import com.basantandevloper.geo.muslimamaliyah.utils.Constant;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class QosidahFragment extends Fragment  {
    View view;
    SearchView searchView;
    TextView tx_totalData;

    DatabaseHelper databaseHelper;
    private QosidahAdapter adapter;
    private ArrayList<QosidahModel> qosidahArrayList;
    private QosidahModel qosidahModel;
    private IsiQasidah qasidah;
    AppCompatButton appCompatButton;

    RecyclerView recyclerView;
    boolean isLoading = false;
    boolean isMore = true;

    Integer total ;

    ProgressDialog progress;
    private static final Type REVIEW_TYPE = new TypeToken<List<QosidahModel>>() {
    }.getType();

    public QosidahFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_qosidah, container, false);
        setHasOptionsMenu(true);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        appCompatButton = (AppCompatButton)view.findViewById(R.id.btnAudio);
        qosidahArrayList =  new ArrayList<>();
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        databaseHelper = new DatabaseHelper(getActivity());
        Integer count = databaseHelper.getJumlahDataQosidah();
        total = count;
        tx_totalData = (TextView)view.findViewById(R.id.total_data);
        tx_totalData.setText(String.valueOf(count)+" Qosidah");
        getDataSQLite(50);

        if (qosidahArrayList.size()<=0){
            getDataFromServer();
        }
        initAdapter();
        initScrollListener();

        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SongActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void getDataFromServer() {
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(getActivity());
        //  progressDoalog.setMax(10);
        progressDoalog.setMessage("Mendownload Data Qosidah dan Menyimpan ke Perangkat Anda, Pastikan Koneksi Internet Anda Terhubung..");
        progressDoalog.setTitle("Mohon Tunggu....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

        ApiInterface apiInterface = APIClient.getClientSheet().create(ApiInterface.class);
        Call<Mqasidah> mqasidahCall = apiInterface.getQasidah("isi_qasidah");
        mqasidahCall.enqueue(new Callback<Mqasidah>() {
            @Override
            public void onResponse(Call<Mqasidah> call, Response<Mqasidah> response) {
                if (response.isSuccessful()){
                    deleteDetail();
                    int jumlah = response.body().getIsiQasidah().size();
                    for (int i=0;i<jumlah;i++){
                        saveDetailtoSQLite(String.valueOf(i+1),
                                response.body().getIsiQasidah().get(i).getIdQasidah(),
                                response.body().getIsiQasidah().get(i).getArab(),
                                response.body().getIsiQasidah().get(i).getArti(),
                                response.body().getIsiQasidah().get(i).getJudul());
                    }
                    groupDataAndSave();
                    progressDoalog.dismiss();
                }else{
                    Log.d("NOTRESPON", "onResponse: "+response.code());
                    progressDoalog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Mqasidah> call, Throwable t) {
                Log.e("NOTRESPON", "onResponse: rrrr");
                progressDoalog.dismiss();
            }
        });
    }

    private void groupDataAndSave() {
        deletetoSqlite();
        databaseHelper.getGroupIsiQosidah();
        qosidahArrayList.clear();
        qosidahArrayList.addAll(databaseHelper.getAllDaftarQosidah(50));
        initAdapter();
        adapter.notifyDataSetChanged();


        tx_totalData.setText(String.valueOf(databaseHelper.getJumlahDataQosidah())+" Qosidah");

    }

    private void getDataSQLite(Integer limit) {
        qosidahArrayList.clear();
        qosidahArrayList.addAll(databaseHelper.getAllDaftarQosidah(limit));

    }
    private void deletetoSqlite(){
        databaseHelper = new DatabaseHelper(getActivity());
        databaseHelper.deleteAllDafQosidah();
    }

    private void saveDetailtoSQLite(String id, String id_qosidah, String arab, String arti, String judul) {
        qasidah = new IsiQasidah(id,id_qosidah,arab,arti,judul);
        qasidah.setId(id);
        qasidah.setIdQasidah(id_qosidah);
        qasidah.setArab(arab);
        qasidah.setArti(arti);
        qasidah.setJudul(judul);
        databaseHelper.addQosidah(qasidah);

    }

    private void deleteDetail(){
        databaseHelper = new DatabaseHelper(getActivity());
        databaseHelper.deleteAllQosidah();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main,menu);
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
                adapter = new QosidahAdapter(qosidahArrayList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);

                return false;
            }
        });
    }

    private void getFilterData(String cari) {
        qosidahArrayList.clear();
        isMore = false;

        qosidahArrayList.addAll(databaseHelper.getCariDaftarQosidah(cari));
    }

    private void initAdapter() {
        adapter = new QosidahAdapter(qosidahArrayList);
        recyclerView.setAdapter(adapter);
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
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == qosidahArrayList.size() - 1) {
                        //bottom of list!
                        if (isMore){
                            loadMore2();
                        }
                        isLoading = true;
                    }
                }
            }
        });
    }
    private void loadMore2(){
        qosidahArrayList.add(null);
        adapter.notifyItemInserted(qosidahArrayList.size() - 1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                qosidahArrayList.remove(qosidahArrayList.size() - 1);
                int scrollPosition = qosidahArrayList.size();
                adapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + 50;
                if (nextLimit >= total){
                    nextLimit = total;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_sinkron:
                showDialogSinkron();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialogSinkron() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Update Data Terbaru")
                .setMessage("Ingin mengupdate data terbaru.\ndari server kami ?")
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        qosidahArrayList =  new ArrayList<>();
                        qosidahArrayList.clear();
                        getDataFromServer();
                    }
                })
                .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }
}
