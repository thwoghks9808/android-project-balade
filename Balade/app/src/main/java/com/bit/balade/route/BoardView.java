package com.bit.balade.route;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bit.balade.LoginActivity.MemberVO;
import com.bit.balade.R;
import com.bit.balade.Statistics.StatAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BoardView extends AppCompatActivity {

    private List<BoardData> al;
    private BoardAdapter ba;
    private RecyclerView rv;
    private LinearLayoutManager lm;

    TextView bv_tv_title,bv_tv_userid, bv_tv_content, bv_tv_reply;
    EditText bv_et_reply;
    Button bv_btn_rpost;
    RouteData rd;


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
        setContentView(R.layout.activity_board_view);
        bv_tv_title = findViewById(R.id.bv_tv_title);
        bv_tv_userid = findViewById(R.id.bv_tv_userid);


        bv_tv_content = findViewById(R.id.bv_tv_content);
        bv_tv_reply = findViewById(R.id.bv_tv_reply);
        bv_et_reply = findViewById(R.id.bv_et_reply);
        bv_btn_rpost = findViewById(R.id.bv_btn_rpost);

        setRetrofitInit();

        Intent i = getIntent();
        rd = i.getParcelableExtra("routeData");
        bv_tv_title.setText(rd.getTitle());
        bv_tv_userid.setText(rd.getUser_id() + "  |  " + rd.getTime());
        bv_tv_content.setText(rd.getContent());

        MemberVO memberVO = i.getParcelableExtra("memberVO");
        String user_id = memberVO.getUser_id();

        bv_btn_rpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                BoardData bd = new BoardData("barcode", "I fucking love ya all");
//                al.add(bd);
//                ba.notifyDataSetChanged();
                addReply(user_id);
                updateComment();

            }
        });


        rv = findViewById(R.id.bv_rt_rev_reply);
        lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        al = new ArrayList<>();

        updateComment();

    }
    private  void updateComment(){
        Call<PostVo> call = service.getReply(rd.getNo());
        call.enqueue(new Callback<PostVo>() {
            @Override
            public void onResponse(Call<PostVo> call, Response<PostVo> response) {
                PostVo postVo = response.body();
                al = postVo.getReply();
                ba = new BoardAdapter(BoardView.this, al);
                rv.setAdapter(ba);
                ba.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<PostVo> call, Throwable t) {

            }
        });
    }

    private void addReply(String userid){
        String content = bv_et_reply.getText().toString();
        String user_id = userid;
        String no = rd.getNo();

        if(content != null){
            Call<PostVo> call = service.addReply(no, user_id, content);
            call.enqueue(new Callback<PostVo>() {
                @Override
                public void onResponse(Call<PostVo> call, Response<PostVo> response) {
                    PostVo postVo = response.body();

                    Toast.makeText(getApplicationContext(), "답글이 작성되었습니다.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<PostVo> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "답글 작성에 실패하였습니다. 서버오류 입니다.", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(getApplicationContext(), "답글은 비어있을수 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}