package com.basantandevloper.geo.muslimamaliyah.MediaPlayer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.basantandevloper.geo.muslimamaliyah.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class SongAdapter  extends ArrayAdapter<ModelData> {
    private Activity context;
    private List<ModelData> Songs;
    String databaseReference;

    public SongAdapter(Activity context, List<ModelData> Songs) {
        super(context, R.layout.list_song, Songs);
        this.context = context;
        this.Songs = Songs;
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View listview = inflater.inflate(R.layout.list_song , null , true);
        TextView textViewName  = (TextView) listview.findViewById(R.id.textName);
        ImageView imageView  = (ImageView) listview.findViewById(R.id.imgArtis);
        TextView textViewArt  = (TextView) listview.findViewById(R.id.textArtis);
        final ModelData Mod = Songs.get(position);
        textViewName.setText(Mod.name);
        Glide.with(context).load(Mod.image).into(imageView);
        textViewArt.setText(Mod.artist+" Vol "+Mod.vol);

        return listview;

    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}
