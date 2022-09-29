package com.bit.balade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bit.balade.RegisterActivity.JoinVO;
import com.bit.balade.RegisterActivity.ServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class register extends AppCompatActivity {
    EditText et_email;
    EditText et_pwd_reg;
    EditText et_id_reg;
    Button btn_register;
    ImageView iv_logo;

    TextView tv_find_reg;
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
        setContentView(R.layout.activity_register);
        et_email = (EditText) findViewById(R.id.et_email);
        et_pwd_reg = (EditText) findViewById(R.id.et_pwd_reg);
        et_id_reg = (EditText) findViewById(R.id.et_id_reg);
        btn_register = (Button) findViewById(R.id.btn_register);
        tv_find_reg = (TextView) findViewById(R.id.tv_find_reg);
        iv_logo = findViewById(R.id.iv_logo);


        setRetrofitInit();


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptJoin();
            }
        });

        tv_find_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(register.this, Lost.class);
                startActivity(intent);
            }
        });
    }
    private void attemptJoin(){
        et_email.setError(null);
        et_pwd_reg.setError(null);
        et_id_reg.setError(null);

        String reg_email = et_email.getText().toString();
        String reg_pwd = et_pwd_reg.getText().toString();
        String reg_id = et_id_reg.getText().toString();



        // 비밀번호 검사
        if(reg_pwd.isEmpty()){
            et_pwd_reg.setError("비밀번호를 입력해주세요");
        }else if(!isPasswordValid(reg_pwd)){
            et_pwd_reg.setError("비밀번호는 6자리를 넘어야 합니다");
        }

        // 이메일 검사
        else if (reg_email.isEmpty()) {
            et_email.setError("이메일을 입력해주세요.");
        } else if (!isEmailValid(reg_email)) {
            et_email.setError("이메일에는 @가 포함되어야 합니다");
        }

        // id 검사
        else if (reg_id.isEmpty()) {
            et_id_reg.setError("아이디를 입력해주세요.");
        }
        else {
            startJoin();
        }
    }
    private void startJoin(){
        String id = et_id_reg.getText().toString();
        String pwd = et_pwd_reg.getText().toString();
        String email = et_email.getText().toString();

        ServiceApi service = retrofit.create(ServiceApi.class);
        Call<JoinVO>  call = service.userJoin(id, pwd, email);

        call.enqueue(new Callback<JoinVO>() {
            @Override
            public void onResponse(Call<JoinVO> call, Response<JoinVO> response) {
                JoinVO joinVo = response.body();

                if(joinVo != null){
                    Toast.makeText(register.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(register.this, Login.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
                    Log.e("register Fail", String.valueOf(joinVo));
                }
            }

            @Override
            public void onFailure(Call<JoinVO> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "서버에서 데이터를 가져오는데 실패 했습니다.", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private boolean isEmailValid(String user_email){
        return user_email.contains("@");
    }
    private boolean isPasswordValid(String user_pwd){
        return user_pwd.length() >= 6;
    }

}