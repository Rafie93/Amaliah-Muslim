package com.basantandevloper.geo.muslimamaliyah.Doa;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.basantandevloper.geo.muslimamaliyah.R;

import java.util.ArrayList;
import java.util.List;

public class DoaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    public ArrayList<ListDoa> listDoa;
    public ArrayList<ListDoa> listDoasFull;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public DoaAdapter(ArrayList<ListDoa> listDoa) {
        this.listDoa = listDoa;
        listDoasFull = new ArrayList<>(listDoa);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.list_doa, parent, false);
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
        int urut = position+1;
        holder.txtNo.setText(String.valueOf(urut));
        holder.txtJudul.setText(listDoa.get(position).getJudul());
        holder.cvDoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),DetailDoaActivity.class);
                intent.putExtra("id",String.valueOf(listDoa.get(position).getId()));
                intent.putExtra("judul",listDoa.get(position).getJudul());
                intent.putExtra("isi_arab",listDoa.get(position).getIsiArab());
                intent.putExtra("latin",listDoa.get(position).getLatin());
                intent.putExtra("arti",listDoa.get(position).getArti());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (listDoa != null) ? listDoa.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return listDoa.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public Filter getFilter() {
        return listFilter;
    }
    private Filter listFilter = new Filter(){
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<ListDoa> filterList = new ArrayList<>();
            if (constraint ==null || constraint.length()==0){
                filterList.addAll(listDoasFull);
            }else{
                String filterPatern = constraint.toString().toLowerCase().toLowerCase().trim();
                for (ListDoa item : listDoasFull){
                    if (item.getJudul().toLowerCase().contains(filterPatern)){
                        filterList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listDoa.clear();
            listDoa.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView txtJudul,txtNo;
        private CardView cvDoa;
        public ItemViewHolder(View itemView) {
            super(itemView);
            txtNo = (TextView)itemView.findViewById(R.id.rowId);
            txtJudul = (TextView)itemView.findViewById(R.id.rowArab);
            cvDoa = (CardView)itemView.findViewById(R.id.cvDoa);
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
