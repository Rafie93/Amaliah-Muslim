package com.basantandevloper.geo.muslimamaliyah.Doa;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import com.basantandevloper.geo.muslimamaliyah.API.APIClient;
import com.basantandevloper.geo.muslimamaliyah.API.ApiInterface;
import com.basantandevloper.geo.muslimamaliyah.Database.DatabaseHelper;
import com.basantandevloper.geo.muslimamaliyah.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoaFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    private DatabaseHelper databaseHelper;
    private DoaAdapter adapter;
    private DoaModel doaModels;
    private ArrayList<ListDoa> listDoas;
    private ListDoa doa;

    boolean isMore = true;
    boolean isLoading = false;
    int total;

    public DoaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_doa, container, false);
        setHasOptionsMenu(true);
        recyclerView  = (RecyclerView)view.findViewById(R.id.recycler_view);

        listDoas =  new ArrayList<>();
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        databaseHelper = new DatabaseHelper(getActivity());

        total = databaseHelper.getJumlahDataDoa();

        getDataSQLite(50);
        if (listDoas.size()<=0){
            loadData();
        }

        initAdapter();
        initScrollListener();


        return view;
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
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == listDoas.size() - 1) {
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
        listDoas.add(null);
        adapter.notifyItemInserted(listDoas.size() - 1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listDoas.remove(listDoas.size() - 1);
                int scrollPosition = listDoas.size();
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
        }, 1000);
    }

    private void initAdapter(){
        adapter = new DoaAdapter(listDoas);
        recyclerView.setAdapter(adapter);
    }
    void loadData(){
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(getActivity());
        //  progressDoalog.setMax(10);
        progressDoalog.setMessage("Mendownload Doa dan Menyimpan ke Perangkat Anda, Proses Ini memakan Waktu, Mohon Tunggu....");
        progressDoalog.setTitle("Menyimpan Data....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

        ApiInterface apiInterface = APIClient.getClientSheet().create(ApiInterface.class);
        Call<DoaModel> call = apiInterface.getDoa("list_doa");
        call.enqueue(new Callback<DoaModel>() {
            @Override
            public void onResponse(Call<DoaModel> call, Response<DoaModel> response) {
                if (response.isSuccessful()){
                    deletetoSqlite();
                    Log.d("RESPONSE",response.toString());
                    int jumlah = response.body().getListDoa().size();
                    total = jumlah;
                    Log.d("JUMLAH", "onResponse: "+jumlah);
                    for (int i=0;i<jumlah;i++){
                        savetoSQLite(response.body().getListDoa().get(i).getId(),
                                response.body().getListDoa().get(i).getJudul(),
                                response.body().getListDoa().get(i).getIsiArab(),
                                response.body().getListDoa().get(i).getArti(),
                                response.body().getListDoa().get(i).getLatin());

                        listDoas.add(new ListDoa( response.body().getListDoa().get(i).getId(),
                                response.body().getListDoa().get(i).getJudul(),
                                response.body().getListDoa().get(i).getIsiArab(),
                                response.body().getListDoa().get(i).getArti(),
                                response.body().getListDoa().get(i).getLatin()
                                ));
                    }

                    adapter = new DoaAdapter(listDoas);
                    recyclerView.setAdapter(adapter);
                    progressDoalog.dismiss();
                    if (adapter.getItemCount()<1){
                        Toast.makeText(getActivity(),"Data Tidak Ditemukan",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Log.d("NOTRESPON", "onResponse: "+response.code());
                    progressDoalog.dismiss();
                    Toast.makeText(getActivity(),"Periksa Koneksi Internet Anda",Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onFailure(Call<DoaModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Periksa Koneksi Internet Anda",Toast.LENGTH_LONG).show();
                progressDoalog.dismiss();
            }
        });
    }

    void getDataSQLite(Integer limit){
        listDoas.clear();
        listDoas.addAll(databaseHelper.getAllDoa(limit));

    }
    void getFilterData(String cari){
        listDoas.clear();
        isMore = false;
        listDoas.addAll(databaseHelper.getCariDoa(cari));
        if (listDoas.size()>0){
            adapter.notifyDataSetChanged();
        }
    }
    void savetoSQLite(Integer val1,String val2, String val3, String val4, String val5){
        doa = new ListDoa(val1,val2,val3,val4,val5);
        doa.setId(val1);
        doa.setJudul(val2);
        doa.setIsiArab(val3);
        doa.setArti(val4);
        doa.setLatin(val5);
        databaseHelper.addDoa(doa);

    }
    void deletetoSqlite(){
        databaseHelper = new DatabaseHelper(getActivity());
        databaseHelper.deleteDoa();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
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
               // adapter.getFilter().filter(newText);
                getFilterData(newText);
                return false;
            }
        });

        //   super.onCreateOptionsMenu(menu, inflater);
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
                        listDoas =  new ArrayList<>();
                        listDoas.clear();
                        loadData();
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
