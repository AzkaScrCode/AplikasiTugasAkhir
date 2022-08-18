package com.example.aplikasitugasakhir.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasitugasakhir.R;
import com.example.aplikasitugasakhir.model.NetLaju;
import com.example.aplikasitugasakhir.model.WaterBalance;

import java.util.List;

public class GraphPrediksiAdapter extends RecyclerView.Adapter<GraphPrediksiAdapter.WaterReqViewHolder> {

    private List<NetLaju> adapters;

    public GraphPrediksiAdapter() {
    }

    public GraphPrediksiAdapter(List<NetLaju> adapters) {
        this.adapters = adapters;
    }

    @NonNull
    @Override
    public WaterReqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_graph_prediksi_item, parent, false);
        return new WaterReqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WaterReqViewHolder holder, int position) {
        NetLaju netLaju = adapters.get(position);

        String netlaju = String.valueOf(netLaju.getNetLaju());

        holder.mHari.setText(String.valueOf(netLaju.getHari()));
        holder.mNetLaju.setText(netlaju);
    }

    @Override
    public int getItemCount() {
        return adapters.size();
    }

    public class WaterReqViewHolder extends RecyclerView.ViewHolder {

        TextView mHari, mNetLaju;

        public WaterReqViewHolder(@NonNull View itemView) {
            super(itemView);

            mHari = itemView.findViewById(R.id.tv_hari_item);
            mNetLaju = itemView.findViewById(R.id.tv_netlaju_item);
        }
    }
}
