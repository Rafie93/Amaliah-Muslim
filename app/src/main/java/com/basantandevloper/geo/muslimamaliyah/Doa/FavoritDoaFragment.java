package com.basantandevloper.geo.muslimamaliyah.Doa;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basantandevloper.geo.muslimamaliyah.Database.DatabaseHelper;
import com.basantandevloper.geo.muslimamaliyah.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritDoaFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    ArrayList<ListDoa> listDoas;
    DoaAdapter doaAdapter;
    protected Cursor cursor;
    private DatabaseHelper databaseHelper;

    public FavoritDoaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_favorit_doa, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_favorit);
        recyclerView.setNestedScrollingEnabled(false);

        initObjects();
        return view;
    }
    void initObjects(){
        listDoas = new ArrayList<>();
        doaAdapter = new DoaAdapter(listDoas);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(doaAdapter);
        databaseHelper = new DatabaseHelper(getActivity());

        getDataFromSQLite();
    }
    void getDataFromSQLite(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listDoas.clear();
                listDoas.addAll(databaseHelper.getFavDoa());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                doaAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

}
