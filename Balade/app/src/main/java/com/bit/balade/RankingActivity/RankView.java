package com.bit.balade.RankingActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bit.balade.GpsActivity.GpsActivity;
import com.bit.balade.LoginActivity.MemberVO;
import com.bit.balade.Menu;
import com.bit.balade.R;
import com.bit.balade.Statistics.StatActivity;
import com.bit.balade.route.BoardAPI;
import com.bit.balade.route.route;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RankView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private List<RankData> rankDataList;
    private RankAdapter rankAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    NavigationView navigationView;
    DrawerLayout drawerLayout;

    TextView rk_tv_title, rk_tv_header;
    ImageButton rk_btn_menu;


    private Retrofit retrofit;
    private RankAPI service;
    private void setRetrofitInit(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://3.34.1.154:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RankAPI.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_view);

        rk_tv_title = findViewById(R.id.rk_tv_title);
        rk_tv_header = findViewById(R.id.rk_tv_header);
        rk_btn_menu = findViewById(R.id.rk_btn_menu);

        Intent i = getIntent();
        MemberVO memberVO = i.getParcelableExtra("memberVO");
        String userID = memberVO.getUser_id();

        recyclerView = findViewById(R.id.rk_rev);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        rankDataList = new ArrayList<>();

        setRetrofitInit();
        setNavigationViewListener();

        Call<RankVO> call = service.getRankPage(userID);
        call.enqueue(new Callback<RankVO>() {
            @Override
            public void onResponse(Call<RankVO> call, Response<RankVO> response) {
                RankVO rankVO = response.body();
                rankDataList = rankVO.getRanking();
                rankAdapter = new RankAdapter(RankView.this, rankDataList);
                recyclerView.setAdapter(rankAdapter);
                rankAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<RankVO> call, Throwable t) {
                Log.e("Error", "Connecting to Server Failed");
                Toast.makeText(getApplicationContext(), "다시 시도해주세요", Toast.LENGTH_SHORT).show();
            }
        });

        rk_btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    public void setNavigationViewListener(){
        navigationView = findViewById(R.id.rk_nav_view);
        navigationView.setNavigationItemSelectedListener(RankView.this);
        drawerLayout = findViewById(R.id.rk_drawer_layout);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.item_main_menu:
                Intent toMenu = new Intent(getApplicationContext(), Menu.class);
                startActivity(toMenu);
                break;
            case R.id.item_gps:
                Intent toGps = new Intent(getApplicationContext(), GpsActivity.class);
                startActivity(toGps);
                break;
            case R.id.item_stat:
                Intent toStat = new Intent(getApplicationContext(), StatActivity.class);
                startActivity(toStat);
                break;
            case R.id.item_general:
                Intent toGen = new Intent(getApplicationContext(), route.class);
                startActivity(toGen);
                break;
            case R.id.item_rank:
                Intent toRank = new Intent(getApplicationContext(), RankView.class);
                startActivity(toRank);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}