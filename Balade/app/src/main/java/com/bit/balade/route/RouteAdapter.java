package com.bit.balade.route;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bit.balade.LoginActivity.MemberVO;
import com.bit.balade.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.CustomViewHolder> {

    private Context mContext;
    private List<RouteData> arrayList;
    private MemberVO m;
    private  PostVo p;

    private Retrofit retrofit;
    private BoardAPI service;
    private void setRetrofitInit(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://3.34.1.154:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(BoardAPI.class);
    }


    public RouteAdapter(Context context, List<RouteData> arrayList, MemberVO m, PostVo p) {
        this.arrayList = arrayList;
        this.mContext = context;
        this.m = m;
        this.p = p;
    }

    @NonNull
    @Override
    public RouteAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routecom_list,parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RouteAdapter.CustomViewHolder holder, int position) {
        holder.rt_tv_title.setText(arrayList.get(position).getTag() +" "+arrayList.get(position).getTitle());
        holder.rt_tv_username.setText(arrayList.get(position).getUser_id());
        holder.rt_tv_date.setText(arrayList.get(position).getTime());
        setRetrofitInit();


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                remove(holder.getBindingAdapterPosition());
                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, BoardView.class);
                i.putExtra("routeData", (Parcelable) arrayList.get(holder.getBindingAdapterPosition()));
                i.putExtra("memberVO", m);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public void remove(int position){
        try{
            deleteArticle(position);
            arrayList.remove(position);
            notifyItemRemoved(position);
        }catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView rt_tv_title, rt_tv_username, rt_tv_date;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.rt_tv_title = itemView.findViewById(R.id.rt_tv_title);
            this.rt_tv_username = itemView.findViewById(R.id.rt_tv_username);
            this.rt_tv_date = itemView.findViewById(R.id.rt_tv_date);

        }
    }


    public void deleteArticle(int i){
        String no = arrayList.get(i).getNo();

        Call<PostVo> call = service.deleteArticle(no);
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
