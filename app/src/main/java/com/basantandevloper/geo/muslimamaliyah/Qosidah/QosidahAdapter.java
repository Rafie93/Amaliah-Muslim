package com.basantandevloper.geo.muslimamaliyah.Qosidah;

import android.content.Context;
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

public class QosidahAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    public ArrayList<QosidahModel> qosidahModels;
    public ArrayList<QosidahModel> qosidahModelsFilter;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    Context context;

    public QosidahAdapter(ArrayList<QosidahModel> qosidahModels) {
        this.qosidahModels = qosidahModels;
        this.qosidahModelsFilter = new ArrayList<>(qosidahModels);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.listqosidah, parent, false);
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

//    public void onBindViewHolder(@NonNull QosidahViewHolder holder, final int position) {
//        holder.txtId.setText(qosidahModels.get(position).getId());
//        holder.txtJudul.setText(qosidahModels.get(position).getJudul());
//        holder.cvQosidah.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(),DetailQosidahActivity.class);
//                intent.putExtra("id",qosidahModels.get(position).getId());
//                intent.putExtra("nama",qosidahModels.get(position).getJudul());
//                v.getContext().startActivity(intent);
//
//            }
//        });
//    }

    @Override
    public int getItemCount() {
        return (qosidahModels != null) ? qosidahModels.size() : 0;
     //   return 30;
    }

    @Override
    public int getItemViewType(int position) {
        return qosidahModels.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public Filter getFilter() {
        return filterData;
    }
    private Filter filterData = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<QosidahModel> filterList = new ArrayList<>();
            if (constraint ==null || constraint.length()==0){
                filterList.addAll(qosidahModelsFilter);
            }else{
                String filterPatern = constraint.toString().toLowerCase().toLowerCase().trim();
                for (QosidahModel item : qosidahModelsFilter){
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
            qosidahModels.clear();
            qosidahModels.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView txtId,txtJudul;
        private CardView cvQosidah;

        public ItemViewHolder(final View itemView) {
            super(itemView);
            txtId = (TextView)itemView.findViewById(R.id.rowNomor);
            txtJudul = (TextView)itemView.findViewById(R.id.rowJudul);
            cvQosidah = (CardView)itemView.findViewById(R.id.cvQosidah);
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

    private void populateItemRows(ItemViewHolder viewHolder, final int position) {
        viewHolder.txtId.setText(qosidahModels.get(position).getId());
        viewHolder.txtJudul.setText(qosidahModels.get(position).getJudul());
        viewHolder.cvQosidah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),DetailQosidahActivity.class);
                intent.putExtra("id",qosidahModels.get(position).getId());
                intent.putExtra("nama",qosidahModels.get(position).getJudul());
                v.getContext().startActivity(intent);

            }
        });
    }
}
