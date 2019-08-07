package com.basantandevloper.geo.muslimamaliyah.Dzikir;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.basantandevloper.geo.muslimamaliyah.Database.DatabaseHelper;
import com.basantandevloper.geo.muslimamaliyah.R;

import java.util.ArrayList;

public class DzikirAdapter extends RecyclerView.Adapter<DzikirAdapter.DzikirMyViewHolder> {
    private ArrayList<DzikirModel> dzikirList;
    private Context context;
    private ArrayList<DzikirModel> mDzikirFilter;
    private DatabaseHelper databaseHelper;

    public DzikirAdapter(ArrayList<DzikirModel> dzikirList, Context context) {
        this.dzikirList = dzikirList;
        this.context = context;
        this.mDzikirFilter = dzikirList;
    }

    @NonNull
    @Override
    public DzikirMyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_dzikir,parent,false);

        return new DzikirMyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DzikirMyViewHolder holder, final int position) {
        final String nama = dzikirList.get(position).getNama();
        final String arti = dzikirList.get(position).getArti();
        final String bacaan = dzikirList.get(position).getBacaan();
        final String hitungan = dzikirList.get(position).getHitungan();
        final Integer id = dzikirList.get(position).getId();

        holder.textViewNama.setText(nama);
        holder.textViewArti.setText(arti);
        holder.textViewBacaan.setText(bacaan);
        holder.textViewHitungan.setText(hitungan);
        holder.textViewTotal.setText(hitungan);

        holder.linearClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MulaiDzikirActivity.class);
                intent.putExtra("nama",nama);
                intent.putExtra("arti",arti);
                intent.putExtra("bacaan",bacaan);
                intent.putExtra("hitungan",hitungan);
                intent.putExtra("id_bacaan",String.valueOf(id));

                context.startActivity(intent);
            }
        });

        holder.imgOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.imgOptions);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu_dzikir);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu1:
                                Intent intentL = new Intent(context,MulaiDzikirActivity.class);
                                intentL.putExtra("nama",nama);
                                intentL.putExtra("arti",arti);
                                intentL.putExtra("bacaan",bacaan);
                                intentL.putExtra("hitungan",hitungan);
                                intentL.putExtra("id_bacaan",String.valueOf(id));
                                context.startActivity(intentL);
                                break;
                            case R.id.menu2:
                                Intent intentE = new Intent(context,UbahDzikirActivity.class);
                                intentE.putExtra("nama",nama);
                                intentE.putExtra("arti",arti);
                                intentE.putExtra("bacaan",bacaan);
                                intentE.putExtra("hitungan",hitungan);
                                intentE.putExtra("id_bacaan",String.valueOf(id));
                                context.startActivity(intentE);
                                break;
                            case R.id.menu3:
                                databaseHelper = new DatabaseHelper(context);
                                databaseHelper.deleteDzikir(String.valueOf(id));
                                Toast.makeText(context,"Dzikir ini "+nama+" telah dihapus",Toast.LENGTH_LONG).show();
                                dzikirList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position,dzikirList.size());

                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dzikirList.size();
    }

    public class DzikirMyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView textViewNama;
        public AppCompatTextView textViewArti;
        public AppCompatTextView textViewBacaan;
        public AppCompatTextView textViewHitungan;
        public AppCompatTextView textViewTotal;
        public ImageView imgOptions;
        public LinearLayout linearClick;

        @SuppressLint("WrongViewCast")
        public DzikirMyViewHolder(View itemView) {
            super(itemView);
            textViewNama = (AppCompatTextView)itemView.findViewById(R.id.rowNamaDzikir);
            textViewArti = (AppCompatTextView)itemView.findViewById(R.id.rowArtiDzikir);
            textViewBacaan = (AppCompatTextView)itemView.findViewById(R.id.rowBacaan);
            textViewHitungan = (AppCompatTextView)itemView.findViewById(R.id.rowHitungan);
            textViewTotal = (AppCompatTextView)itemView.findViewById(R.id.rowTotal);
            imgOptions = (ImageView)itemView.findViewById(R.id.imgOptions);
            linearClick = (LinearLayout)itemView.findViewById(R.id.linearClick);
        }
    }
}
