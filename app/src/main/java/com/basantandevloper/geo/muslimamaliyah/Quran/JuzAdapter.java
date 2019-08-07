package com.basantandevloper.geo.muslimamaliyah.Quran;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.basantandevloper.geo.muslimamaliyah.R;

import java.util.ArrayList;

public class JuzAdapter  extends RecyclerView.Adapter<JuzAdapter.MyViewHolder> {
    public ArrayList<JuzModel> juzModels;

    public JuzAdapter(ArrayList<JuzModel> juzModels) {
        this.juzModels = juzModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.list_juz, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Integer no = position+1;
        final String juz = juzModels.get(position).getIndex();
        final String mulaiSurah =  juzModels.get(position).getStartSurah();
        final String mulaiAyat =  juzModels.get(position).getStartAyat();
        final String endSurah =  juzModels.get(position).getEndSurah();
        final String endAyat =  juzModels.get(position).getEndAyat();
        final String mulaiName =  juzModels.get(position).getStartName();
        final String endName =  juzModels.get(position).getEndName();

        holder.txtNo.setText(String.valueOf(no));
        holder.txtJuz.setText("Juz "+juz);
        holder.txtMulaiName.setText(mulaiName);
        holder.txtMulaiAyat.setText(mulaiAyat);
        holder.txtEndName.setText(endName);
        holder.txtEndAyat.setText(endAyat);

        holder.cvJuz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),JuzActivity.class);
                intent.putExtra("juz",juz);
                intent.putExtra("mulaiSurah",mulaiSurah);
                intent.putExtra("mulaiAyat",mulaiAyat);
                intent.putExtra("endSurah",endSurah);
                intent.putExtra("endAyat",endAyat);
                intent.putExtra("mulaiName",mulaiName);
                intent.putExtra("endName",endName);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (juzModels != null) ? juzModels.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNo,txtJuz,txtMulaiAyat,txtEndAyat,txtMulaiName,txtEndName;
        private CardView cvJuz;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNo = (TextView)itemView.findViewById(R.id.rowNomor);
            txtJuz = (TextView)itemView.findViewById(R.id.rowJuz);
            txtMulaiAyat = (TextView)itemView.findViewById(R.id.rowMulaiAyat);
            txtEndAyat = (TextView)itemView.findViewById(R.id.rowEndAyat);
            txtMulaiName = (TextView)itemView.findViewById(R.id.rowMulaiName);
            txtEndName = (TextView)itemView.findViewById(R.id.rowEndName);

            cvJuz = (CardView) itemView.findViewById(R.id.cvJuz);

        }
    }
}
