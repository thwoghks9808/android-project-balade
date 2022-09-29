package com.bit.balade.route;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bit.balade.LoginActivity.MemberVO;
import com.bit.balade.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostActivity extends AppCompatActivity {
    EditText post_et_title, post_et_content;
    Spinner post_sp_route;
    TextView post_tv_title, post_tv_route, post_tv_content;
    Button post_btn_post;
    String[] post_list_item  = {"[추천]", "[모집]", "[신청]", "[잡담]"};
    String TAG, postTitle;

    private ArrayList<RouteData> arrayList;
    private RouteAdapter routeAdapter;
    private RecyclerView recyclerView;

    private Retrofit retrofit;
    private BoardAPI service;
    private void setRetrofitInit(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://3.34.1.154:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(BoardAPI.class);
    }


    private void sendPostArticle(MemberVO m){
        String post_title = post_et_title.getText().toString();
        String post_content = post_et_content.getText().toString();
        String user_no = m.getUser_no();


        Call<PostVo> call = service.sendPostArticle(TAG, post_title, post_content, user_no);
        call.enqueue(new Callback<PostVo>() {
            @Override
            public void onResponse(Call<PostVo> call, Response<PostVo> response) {
                Intent i = new Intent(getApplicationContext(), route.class);
                i.putExtra("memberVO", m);
                startActivity(i);
            }

            @Override
            public void onFailure(Call<PostVo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "글작성 실패, 서버오류입니다.", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        post_tv_title = findViewById(R.id.post_tv_title);
        post_tv_route = findViewById(R.id.post_tv_route);
        post_tv_content = findViewById(R.id.post_tv_content);
        post_et_title = findViewById(R.id.post_et_title);
        post_et_content = findViewById(R.id.post_et_content);
        post_btn_post = findViewById(R.id.post_btn_post);
        post_sp_route = findViewById(R.id.post_sp_route);

        Intent intent = getIntent();
        MemberVO memberVO = intent.getParcelableExtra("memberVO");

        setRetrofitInit();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, post_list_item
        );
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );


        post_sp_route.setAdapter(adapter);
        post_sp_route.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TAG = post_sp_route.getSelectedItem().toString();

                post_btn_post.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendPostArticle(memberVO);
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "태그를 설정해주세요", Toast.LENGTH_SHORT).show();
                post_btn_post.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "태그 미선택시 글작성이 안됩니다.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });




    }
}