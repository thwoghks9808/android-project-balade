package com.bit.balade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bit.balade.LoginActivity.MemberVO;
import com.bit.balade.LostActivity.FindVO;
import com.bit.balade.LostActivity.ServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Lost extends AppCompatActivity {

    TextView tv_find_top;
    EditText et_find_id;
    Button btn_find_id;
    TextView tv_find_bottom;
    EditText et_find_pwd;
    Button btn_find_pwd;
    private ServiceApi service;
    private Retrofit retrofit;

    private void setRetrofitInit(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://3.34.1.154:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);
        tv_find_top = (TextView) findViewById(R.id.tv_find_top);
        et_find_id = (EditText) findViewById(R.id.et_find_id);
        btn_find_id = (Button) findViewById(R.id.btn_find_id);
        tv_find_bottom = (TextView) findViewById(R.id.tv_find_bottom);
        et_find_pwd = (EditText) findViewById(R.id.et_find_pwd);
        btn_find_pwd = (Button) findViewById(R.id.btn_find_pwd);

        setRetrofitInit();

        btn_find_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptFindId();
            }
        });

        btn_find_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptFindPassword();
            }
        });
    }
    private void attemptFindId(){
        et_find_id.setError(null);
        String email = et_find_id.getText().toString();

        if (email.isEmpty()){
            et_find_id.setError("이메일을 입력해주세요");
        }else if(!isEmailValid(email)){
            et_find_id.setError("이메일은 @가 포함되어야 합니다");
        }else{
            startFindID();
        }
    }

    private boolean isEmailValid(String email){
        return email.contains("@");
    }

    private void attemptFindPassword(){
        et_find_pwd.setError(null);
        String id = et_find_pwd.getText().toString();
        if (id.isEmpty()){
            et_find_id.setError("아이디를 입력해주세요");
        }else{
            startFindPassword();
        }
    }

    private void startFindID(){
        String email = et_find_id.getText().toString();
        ServiceApi service = retrofit.create(ServiceApi.class);
        Call<FindVO> call = service.findUserID(email);

        call.enqueue(new Callback<FindVO>() {
            @Override
            public void onResponse(Call<FindVO> call, Response<FindVO> response) {
                FindVO findVO = response.body();

                if(findVO != null){
                    Toast.makeText(Lost.this, "회원님의 아이디는 " + findVO.getUser_id() + " 입니다",
                            Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "존재하지 않는 이메일입니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FindVO> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "서버연결 실패, 다시 시도해주세요", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startFindPassword(){
        String id = et_find_pwd.getText().toString();
        ServiceApi service = retrofit.create(ServiceApi.class);
        Call<FindVO> call = service.findUserPassword(id);

        call.enqueue(new Callback<FindVO>() {
            @Override
            public void onResponse(Call<FindVO> call, Response<FindVO> response) {
                FindVO findVO = response.body();

                if(findVO != null){
                    Toast.makeText(Lost.this, "회원님의 비밀번호는 " + findVO.getUser_pwd() + " 입니다.",
                            Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "존재하지 않는 아이디입니다", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FindVO> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "서버연결 실패, 다시 시도해주세요", Toast.LENGTH_SHORT).show();
            }
        });
    }
}