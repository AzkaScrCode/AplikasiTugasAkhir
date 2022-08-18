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

public class WaterBalanceAdapter extends RecyclerView.Adapter<WaterBalanceAdapter.WaterReqViewHolder> {

    private List<WaterBalance> adapters;

    public WaterBalanceAdapter() {
    }

    public WaterBalanceAdapter(List<WaterBalance> adapters) {
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

        String dry = String.valueOf(waterBalance.getDry());
        String wet = String.valueOf(waterBalance.getWet());
        String normal = String.valueOf(waterBalance.getNormal());

        holder.mHari.setText(String.valueOf(waterBalance.getHari()));
        holder.mDry.setText(dry);
        holder.mWet.setText(wet);
        holder.mNormal.setText(normal);
    }

    @Override
    public int getItemCount() {
        return adapters.size();
    }

    public class WaterReqViewHolder extends RecyclerView.ViewHolder {

        TextView mHari, mWet, mNormal, mDry, mStatus;

        public WaterReqViewHolder(@NonNull View itemView) {
            super(itemView);

            mHari = itemView.findViewById(R.id.tv_hari_item);
            mWet = itemView.findViewById(R.id.tv_wet_item);
            mNormal = itemView.findViewById(R.id.tv_normal_item);
            mDry = itemView.findViewById(R.id.tv_dry_item);
        }
    }
}
