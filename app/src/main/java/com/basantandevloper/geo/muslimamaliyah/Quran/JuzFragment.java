package com.basantandevloper.geo.muslimamaliyah.Quran;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basantandevloper.geo.muslimamaliyah.R;
import com.basantandevloper.geo.muslimamaliyah.helper.LoadJsonAsset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class JuzFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private JuzAdapter adapter;

    private ArrayList<JuzModel> juzModels;


    public JuzFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_juz, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        juzModels = new ArrayList<>();
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        loadJuzResponse();
        initAdapter();
        return view;
    }

    private void loadJuzResponse() {

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(getActivity());
        //  progressDoalog.setMax(10);
        progressDoalog.setMessage("Memuat Data....");
        progressDoalog.setTitle("Loading....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

        juzModels = new ArrayList<>();
        LoadJsonAsset jsonAsset = new LoadJsonAsset();
        String jsonResponse = jsonAsset.loadJSONFromAsset(getContext(), "juz.json");
        JuzModel[] juzJsonResponse = new JuzJsonResponse().parseJSON(jsonResponse);
        List<JuzModel> listJuz = Arrays.asList(juzJsonResponse);

        for (JuzModel sm : listJuz) {
            juzModels.add(new JuzModel(sm.getIndex(),sm.getStartSurah(),sm.getStartAyat(),
                    sm.getEndSurah(),sm.getEndAyat(),sm.getStartName(),sm.getEndName()));
        }
        progressDoalog.dismiss();
    }

    private void initAdapter() {
        adapter = new JuzAdapter(juzModels);
        recyclerView.setAdapter(adapter);
    }

}
