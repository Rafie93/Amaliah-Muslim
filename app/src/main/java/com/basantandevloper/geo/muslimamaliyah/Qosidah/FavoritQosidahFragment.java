package com.basantandevloper.geo.muslimamaliyah.Qosidah;


import android.content.Context;
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
public class FavoritQosidahFragment extends Fragment {
    View view;

    RecyclerView recyclerView;
    protected Cursor cursor;
    private DatabaseHelper databaseHelper;
    Context context ;
    private ArrayList<QosidahModel> qosidahModels;
    private QosidahAdapter qosidahRecyclerAdapter;

    public FavoritQosidahFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_favorit_qosidah, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_favorit);
        recyclerView.setNestedScrollingEnabled(false);

       initObjects();
        return view;
    }
    private void initObjects() {
        qosidahModels = new ArrayList<>();
        qosidahRecyclerAdapter = new QosidahAdapter(qosidahModels);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(qosidahRecyclerAdapter);
        databaseHelper = new DatabaseHelper(getActivity());

        getDataFromSQLite();
    }
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                qosidahModels.clear();
                qosidahModels.addAll(databaseHelper.getAllFavQosidah());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                qosidahRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

}
