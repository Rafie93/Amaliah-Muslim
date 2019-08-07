package com.basantandevloper.geo.muslimamaliyah.Sholat;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.basantandevloper.geo.muslimamaliyah.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SholatFragment extends Fragment {
    View view;
    ListView listView;
    ArrayList<SholatModel> dataModels;
    private static SholatAdapter adapter;

    public SholatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sholat, container, false);
        listView = (ListView)view.findViewById(R.id.list_item);

        loadData();

        adapter= new SholatAdapter(dataModels,getContext().getApplicationContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SholatModel dataModel= dataModels.get(position);
                Intent intent = new Intent(getActivity(), DetailSholatActivity.class);
                intent.putExtra("judul",dataModel.getJudul());
                intent.putExtra("isi",dataModel.getIsi());
                startActivity(intent);

            }
        });

        //adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(), "Maaf Konten ini belum tersedia", Toast.LENGTH_SHORT).show();
//            }
//        });
        return view;

    }

    private void loadData() {
        dataModels= new ArrayList<>();
        try {
            //Load File
            BufferedReader jsonReader = new BufferedReader(new InputStreamReader(getContext().getAssets().open("sholat.json")));
            StringBuilder jsonBuilder = new StringBuilder();
            for (String line = null; (line = jsonReader.readLine()) != null;) {
                jsonBuilder.append(line).append("\n");
            }
            JSONTokener tokener = new JSONTokener(jsonBuilder.toString());
            JSONArray jsonArray = new JSONArray(tokener);

            for (int index = 0; index < jsonArray.length(); index++) {
                JSONObject jsonObject = jsonArray.getJSONObject(index);
                String id = jsonObject.getString("id");
                String judul  = jsonObject.getString("judul");
                String isi  = jsonObject.getString("isi");
                dataModels.add(new SholatModel(id,judul,isi));
            }
        } catch (FileNotFoundException e) {
            Log.e("jsonFile", "file not found");
        } catch (IOException e) {
            Log.e("jsonFile", "ioerror");
        } catch (JSONException e) {
            Log.e("jsonFile", "error while parsing json");
        }
    }

}
