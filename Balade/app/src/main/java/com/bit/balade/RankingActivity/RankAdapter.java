package com.bit.balade.RankingActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bit.balade.R;

import java.util.List;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.RankViewHolder> {

    private Context mContext;
    private List<RankData> arrayList;

    public RankAdapter(Context mContext, List<RankData> arrayList){
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rankcom_list,parent,false);
        RankViewHolder holder = new RankViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RankViewHolder holder, int position) {
        holder.rk_rev_view.setText(arrayList.get(position).getUser_id()+"       |       "+arrayList.get(position).getDistance()
                                    +"km       |       "+arrayList.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class RankViewHolder extends RecyclerView.ViewHolder{

        protected TextView rk_rev_view;

        public RankViewHolder(@NonNull View itemView) {
            super(itemView);
            this.rk_rev_view = itemView.findViewById(R.id.rk_rev_view);
        }
    }
}
