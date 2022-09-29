package com.bit.balade.Statistics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bit.balade.GpsActivity.GpsActivity;
import com.bit.balade.LoginActivity.MemberVO;
import com.bit.balade.Menu;
import com.bit.balade.R;
import com.bit.balade.RankingActivity.RankView;
import com.bit.balade.route.route;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StatActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ImageButton stat_menu;
    View stat_hori_div;
    View stat_hori_div2;
    View stat_ver_div;
    TextView curDisT, curDisR, weekDisR, curTimeT, curTimeR, weekTimeT, weekTimeR;
    Spinner spinner;
    String[] spinner_items = {"현재 기록", "주간 총합 기록"};
    String code;

    NavigationView navigationView;
    DrawerLayout drawerLayout;


    private Retrofit retrofit;
    private StatAPI service;
    private void setRetrofitInit(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://3.34.1.154:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(StatAPI.class);
    }
    private void getSingleCaseStat(String s){
        String user_no = s;
        code = "101";
        Call <StatVO> call = service.getSingleCaseStat(code, user_no);

        call.enqueue(new Callback<StatVO>() {
            @Override
            public void onResponse(Call<StatVO> call, Response<StatVO> response) {
                StatVO statVO = response.body();
                curTimeR.setText(statVO.getTime());
                curDisR.setText(statVO.getDistance() + "Km");
            }

            @Override
            public void onFailure(Call<StatVO> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "통계 가능한 기록이 없습니다", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getWeeklyCaseStat(String s){
        String user_no = s;
        code = "201";
        Call <StatVO> call = service.getWeeklyCaseStat(code, user_no);

        call.enqueue(new Callback<StatVO>() {
            @Override
            public void onResponse(Call<StatVO> call, Response<StatVO> response) {
                StatVO statVO = response.body();
                curTimeR.setText(statVO.getTime());
                curDisR.setText(statVO.getDistance() + "Km");
            }

            @Override
            public void onFailure(Call<StatVO> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "통계 가능한 기록이 없습니다", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getWeeklyBestCase(String s){
        String user_no = s;
        code = "001";

        Call<StatVO> call = service.getWeeklyBestStat(code, user_no);

        call.enqueue(new Callback<StatVO>() {
            @Override
            public void onResponse(Call<StatVO> call, Response<StatVO> response) {
                StatVO statVO = response.body();
                weekDisR.setText(statVO.getDistance()+"Km");
                weekTimeR.setText(statVO.getTime());
            }

            @Override
            public void onFailure(Call<StatVO> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "통계 가능한 기록이 없습니다", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        stat_menu = findViewById(R.id.stat_menu);
        stat_hori_div = findViewById(R.id.stat_hori_div);
        stat_hori_div2 = findViewById(R.id.stat_hori_div2);
        stat_ver_div = findViewById(R.id.stat_ver_div);
        curDisT = findViewById(R.id.stat_tv_distance_t);
        curDisR = findViewById(R.id.stat_tv_distance_r);
        curTimeT = findViewById(R.id.stat_tv_time_t);
        curTimeR = findViewById(R.id.stat_tv_time_r);
        weekTimeT = findViewById(R.id.stat_tv_time_wb);
        weekTimeR = findViewById(R.id.stat_tv_time_wb_r);
        weekDisR = findViewById(R.id.stat_tv_distance_wb_r);
        spinner = findViewById(R.id.spinner);

        setNavigationViewListener();

        setRetrofitInit();

        Intent intent = getIntent();
        MemberVO memberVO = intent.getParcelableExtra("memberVO");
        String userNo = memberVO.getUser_no();

        getWeeklyBestCase(userNo);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinner_items
        );
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0:
                        curTimeT.setText("이동 시간");
                        curDisT.setText("이동 거리");
                        getSingleCaseStat(userNo);
                        break;
                    case 1:
                        curDisT.setText("총 이동 거리");
                        curTimeT.setText("총 이동 시간");
                        getWeeklyCaseStat(userNo);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                curTimeT.setText("이동 시간");
                curDisT.setText("이동 거리");
                getSingleCaseStat(userNo);
            }
        });



        stat_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }
    public void setNavigationViewListener(){
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout = findViewById(R.id.drawer_layout);
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