package com.bit.balade.route;

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
import android.widget.Button;
import android.widget.ImageButton;

import com.bit.balade.GpsActivity.GpsActivity;
import com.bit.balade.LoginActivity.MemberVO;
import com.bit.balade.Menu;
import com.bit.balade.R;
import com.bit.balade.RankingActivity.RankView;
import com.bit.balade.Statistics.StatActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class route extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    RouteData routeData;
    private List<RouteData> routeDataList;
    private RouteAdapter routeAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    ImageButton rt_btn_menu;
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    private Retrofit retrofit;
    private BoardAPI service;
    private void setRetrofitInit(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://3.34.1.154:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(BoardAPI.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        Intent intent = getIntent();
        MemberVO memberVO = intent.getParcelableExtra("memberVO");
        String userNo = memberVO.getUser_no();

        recyclerView = (RecyclerView) findViewById(R.id.rt_rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        setRetrofitInit();
        setNavigationViewListener();

        routeDataList = new ArrayList<>();


        Call<PostVo> call = service.createArticlePage(userNo);
        call.enqueue(new Callback<PostVo>() {
            @Override
            public void onResponse(Call<PostVo> call, Response<PostVo> response) {
                PostVo postVO = response.body();
                routeDataList = postVO.getBoardlist();
                routeAdapter = new RouteAdapter(route.this,routeDataList, memberVO, postVO);
                recyclerView.setAdapter(routeAdapter);
                routeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<PostVo> call, Throwable t) {
                Log.e("Fail", "Fail!!!!!");
            }
        });


        Button btn_post = findViewById(R.id.rt_btn_post);
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PostActivity.class);
                i.putExtra("memberVO", memberVO);
                startActivity(i);

            }
        });

        rt_btn_menu = findViewById(R.id.rt_btn_menu);
        rt_btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }
    public void setNavigationViewListener(){
        navigationView = findViewById(R.id.rt_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout = findViewById(R.id.rt_drawer_layout);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
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