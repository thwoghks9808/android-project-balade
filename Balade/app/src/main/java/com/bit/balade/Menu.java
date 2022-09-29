package com.bit.balade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bit.balade.GpsActivity.GpsActivity;
import com.bit.balade.LoginActivity.MemberVO;
import com.bit.balade.RankingActivity.RankView;
import com.bit.balade.Statistics.StatActivity;
import com.bit.balade.route.BoardView;
import com.bit.balade.route.route;

public class Menu extends AppCompatActivity {

    ImageButton menu_gps, menu_record, menu_rank, menu_free;

    TextView menu_tv_gps, menu_tv_record, menu_tv_rank, menu_tv_free;

    ImageView menu_logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        menu_gps = findViewById(R.id.menu_gps);
        menu_record = findViewById(R.id.menu_record);
        menu_rank = findViewById(R.id.menu_rank);
        menu_free = findViewById(R.id.menu_free);
        menu_logo = findViewById(R.id.menu_logo);
        menu_tv_gps = findViewById(R.id.menu_tv_gps);
        menu_tv_record = findViewById(R.id.menu_tv_record);
        menu_tv_rank = findViewById(R.id.menu_tv_rank);
        menu_tv_free = findViewById(R.id.menu_tv_free);


        Intent intent = getIntent();
        MemberVO memberVO = intent.getParcelableExtra("memberVO");



        menu_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GpsActivity.class);
                intent.putExtra("memberVO", memberVO);
                startActivity(intent);
            }
        });
        menu_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StatActivity.class);
                intent.putExtra("memberVO", memberVO);
                startActivity(intent);
            }
        });
        menu_free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), route.class);
                i.putExtra("memberVO", memberVO);
                startActivity(i);
            }
        });
        menu_rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RankView.class);
                i.putExtra("memberVO", memberVO);
                startActivity(i);
            }
        });
    }
}