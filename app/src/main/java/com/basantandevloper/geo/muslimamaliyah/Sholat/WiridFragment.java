package com.basantandevloper.geo.muslimamaliyah.Sholat;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.basantandevloper.geo.muslimamaliyah.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WiridFragment extends Fragment {
    View view;
    ListView listView;
    ArrayAdapter<CharSequence> adapter;

    public WiridFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_wirid, container, false);
        listView = (ListView)view.findViewById(R.id.list_item);
        adapter = ArrayAdapter.createFromResource(getActivity(),R.array.wirid_arry,android.R.layout.simple_list_item_1);
        //adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Maaf Konten ini belum tersedia", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}
