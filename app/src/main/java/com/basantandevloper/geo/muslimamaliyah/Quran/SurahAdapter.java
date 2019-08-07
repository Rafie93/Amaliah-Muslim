package com.basantandevloper.geo.muslimamaliyah.Quran;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.basantandevloper.geo.muslimamaliyah.R;

import java.util.ArrayList;

public class SurahAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ArrayList<SurahModel>surahModels;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    Context context;


    public SurahAdapter(ArrayList<SurahModel> surahModels) {
        this.surahModels = surahModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.listsurah, parent, false);
            return new ItemViewHolder(view);
        }else{
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.item_loading, parent, false);
           return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            populateItemRows((ItemViewHolder) holder, position);
        } else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, position);
        }
    }

    private void populateItemRows(ItemViewHolder holder, final int position) {
        holder.txtNomor.setText(surahModels.get(position).getNomor());
        holder.txtAyat.setText(surahModels.get(position).getAyat());
        holder.txtAsma.setText(surahModels.get(position).getAsma());
        holder.txtArti.setText(" ("+surahModels.get(position).getArti()+")");
        holder.txtNama.setText(surahModels.get(position).getNama());
        holder.cvSurah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    Toast.makeText(v.getContext(),surahModels.get(position).getName()+" Terpilih",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(),AyatActivity.class);
                intent.putExtra("nomor",surahModels.get(position).getNomor());
                intent.putExtra("nama",surahModels.get(position).getNama());
                intent.putExtra("asma",surahModels.get(position).getAsma());
                intent.putExtra("name",surahModels.get(position).getName());
                intent.putExtra("start",surahModels.get(position).getStart());
                intent.putExtra("type",surahModels.get(position).getType());
                intent.putExtra("urut",surahModels.get(position).getUrut());
                intent.putExtra("rukuk",surahModels.get(position).getRukuk());
                intent.putExtra("arti",surahModels.get(position).getArti());
                intent.putExtra("ayat",surahModels.get(position).getAyat());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (surahModels != null) ? surahModels.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return surahModels.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;

    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNomor,txtNama,txtAsma,txtAyat,txtArti,txtType;
        CardView cvSurah;
        public ItemViewHolder(final View itemView) {
            super(itemView);
            txtNomor = (TextView)itemView.findViewById(R.id.rowSurah);
            txtAyat = (TextView)itemView.findViewById(R.id.rowJumlahAyat);
            txtAsma = (TextView)itemView.findViewById(R.id.rowAsma);
            txtArti = (TextView)itemView.findViewById(R.id.rowTerjemahanSurah);
            txtType = (TextView)itemView.findViewById(R.id.rowtype);
            txtNama = (TextView)itemView.findViewById(R.id.rowAyat);
            cvSurah = (CardView)itemView.findViewById(R.id.cvSurah);
        }
    }
    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingViewHolder(final View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }
}
