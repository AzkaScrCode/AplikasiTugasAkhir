package com.example.aplikasitugasakhir.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasitugasakhir.R;
import com.example.aplikasitugasakhir.model.WaterBalance;

import java.util.List;

public class WaterReqAdapter extends RecyclerView.Adapter<WaterReqAdapter.WaterReqViewHolder> {

    private List<WaterBalance> adapters;

    public WaterReqAdapter() {
    }

    public WaterReqAdapter(List<WaterBalance> adapters) {
        this.adapters = adapters;
    }

    @NonNull
    @Override
    public WaterReqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_water_req_item, parent, false);
        return new WaterReqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WaterReqViewHolder holder, int position) {
        WaterBalance waterBalance = adapters.get(position);
        holder.mHari.setText(String.valueOf(waterBalance.getHari()));
        holder.mDry.setText(String.valueOf(waterBalance.getDry()));
        holder.mWet.setText(String.valueOf(waterBalance.getWet()));
        holder.mNormal.setText(String.valueOf(waterBalance.getNormal()));
    }

    @Override
    public int getItemCount() {
        return adapters.size();
    }

    public class WaterReqViewHolder extends RecyclerView.ViewHolder {

        TextView mHari, mWet, mNormal, mDry;

        public WaterReqViewHolder(@NonNull View itemView) {
            super(itemView);

            mHari = itemView.findViewById(R.id.tv_hari_item);
            mWet = itemView.findViewById(R.id.tv_wet_item);
            mNormal = itemView.findViewById(R.id.tv_normal_item);
            mDry = itemView.findViewById(R.id.tv_dry_item);
        }
    }
}
