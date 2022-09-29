package com.bit.balade.route;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bit.balade.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.CustomViewHolder>{

    private Retrofit retrofit;
    private BoardAPI service;
    private void setRetrofitInit(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://3.34.1.154:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(BoardAPI.class);
    }

    Context mContext;
    private List<BoardData> arrayList;

    public BoardAdapter(Context mContext, List<BoardData> arrayList) {
        this.arrayList = arrayList;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public BoardAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.boardcom_list,parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BoardAdapter.CustomViewHolder holder, int position) {
        holder.bvr_tv_user.setText(arrayList.get(position).getUser_id());
        holder.bvr_tv_content.setText(arrayList.get(position).getContent());
        setRetrofitInit();

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                remove(holder.getBindingAdapterPosition());
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public void remove(int position){
        try{
            deleteReply(position);
            arrayList.remove(position);
            notifyItemRemoved(position);
        }catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView bvr_tv_user, bvr_tv_content;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.bvr_tv_user = itemView.findViewById(R.id.bvr_tv_user);
            this.bvr_tv_content = itemView.findViewById(R.id.bvr_tv_content);

        }
    }

    public void deleteReply(int a){
        String no = arrayList.get(a).getNo();
        String user_id = arrayList.get(a).getUser_id();
        String content = arrayList.get(a).getContent();
        Call<PostVo> call = service.delReply(no, user_id, content);
        call.enqueue(new Callback<PostVo>() {
            @Override
            public void onResponse(Call<PostVo> call, Response<PostVo> response) {
                Log.d("", "Deleted");
            }
            @Override
            public void onFailure(Call<PostVo> call, Throwable t) {
                Log.e("Fail", "Connection Failed");
            }
        });

    }
}
