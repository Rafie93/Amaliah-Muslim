package com.basantandevloper.geo.muslimamaliyah.Qosidah;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.basantandevloper.geo.muslimamaliyah.R;

import java.util.ArrayList;

public class DetailQosidahAdapter extends RecyclerView.Adapter<DetailQosidahAdapter.MyViewHolder> {
    public ArrayList<IsiQasidah> isiQasidahs;
    private Context context;
    public DetailQosidahAdapter(ArrayList<IsiQasidah> isiQasidahs, Context context) {
        this.isiQasidahs = isiQasidahs;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_isi_qosidah, parent, false);
        return new DetailQosidahAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String prefSizeArab = preference.getString(context.getString(R.string.key_list_arabic_size),"0");
        Integer huSize = Integer.valueOf(prefSizeArab);
        Integer ukuruanArab = 22;
        if (huSize == 6){ ukuruanArab = 48;
        }else if  (huSize == 5){ ukuruanArab = 36;
        }else if (huSize == 4){ ukuruanArab = 28;
        }else if  (huSize == 3) { ukuruanArab = 22;
        }else if  (huSize == 2){ ukuruanArab =18;
        }else if  (huSize == 1){ ukuruanArab = 14;
        }else{ ukuruanArab = 22; }

        holder.txtArabic.setTextSize(ukuruanArab);
        holder.txtArabic.setText(isiQasidahs.get(position).getArab());

        holder.txtArt.setText(isiQasidahs.get(position).getArti());

    }

    @Override
    public int getItemCount() {
        return (isiQasidahs != null) ? isiQasidahs.size() : 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtArabic,txtArt;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtArabic = (TextView)itemView.findViewById(R.id.rowIsiArab);
            txtArt = (TextView)itemView.findViewById(R.id.rowTerjemahan);


        }
    }
}
