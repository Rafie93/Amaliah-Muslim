package com.basantandevloper.geo.muslimamaliyah.Asmaul;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.basantandevloper.geo.muslimamaliyah.R;

import java.util.ArrayList;

public class AsmaulHusnaAdapter extends RecyclerView.Adapter<AsmaulHusnaAdapter.MyViewHolder> {
    public ArrayList<AsmaulHusnaModel> asmaulHusnaModels;

    public AsmaulHusnaAdapter(ArrayList<AsmaulHusnaModel> asmaulHusnaModels) {
        this.asmaulHusnaModels = asmaulHusnaModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_asmaul_husna, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtId.setText(asmaulHusnaModels.get(position).getId());
        holder.txtNama.setText(asmaulHusnaModels.get(position).getNama());
        holder.txtArti.setText(asmaulHusnaModels.get(position).getArti());
        holder.txtArab.setText(asmaulHusnaModels.get(position).getArab());

    }

    @Override
    public int getItemCount() {
        return (asmaulHusnaModels != null) ? asmaulHusnaModels.size() : 0;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNama,txtArab,txtArti,txtId;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtId = (TextView)itemView.findViewById(R.id.rowId);
            txtNama = (TextView)itemView.findViewById(R.id.rowNama);
            txtArti = (TextView)itemView.findViewById(R.id.rowArti);
            txtArab = (TextView)itemView.findViewById(R.id.rowAsma);

        }
    }
}
