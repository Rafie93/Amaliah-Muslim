package com.basantandevloper.geo.muslimamaliyah.Sholat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.basantandevloper.geo.muslimamaliyah.R;

import java.util.ArrayList;

public class SholatAdapter extends ArrayAdapter<SholatModel> {

    private ArrayList<SholatModel> dataSet;
    Context mContext;

    public SholatAdapter(ArrayList<SholatModel> data, Context context) {
        super(context, R.layout.list_sholat);
        this.dataSet = data;
        this.mContext = context;
    }


    @Override
    public int getCount() {
        return (dataSet != null) ? dataSet.size() : 0;
    }


    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View listViewItem = inflater.inflate(R.layout.list_sholat, null, true);
;
        TextView textViewName = listViewItem.findViewById(R.id.name);

        SholatModel sholatModel = dataSet.get(position);

        textViewName.setText(sholatModel.getJudul());

        return listViewItem;

    }
}
