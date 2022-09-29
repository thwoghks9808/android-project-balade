package com.bit.balade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bit.balade.LoginActivity.MemberVO;
import com.bit.balade.LoginActivity.LoginService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {
    ImageView iv_logo;
    EditText et_id;
    EditText et_pwd;
    Button btn_login;
    Button btn_reg;
    TextView tv_find;
     private Retrofit retrofit;

    private void setRetrofitInit(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://3.34.1.154:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    private void login(){
        String id = et_id.getText().toString();
        String pwd = et_pwd.getText().toString();

        LoginService service = retrofit.create(LoginService.class);
        Call<MemberVO> call = service.getMember(id, pwd);

        call.enqueue(new Callback<MemberVO>() {
            @Override
            public void onResponse(Call<MemberVO> call, Response<MemberVO> response) {
                MemberVO memberVO = response.body();

                if(memberVO != null){
                    Toast.makeText(Login.this, "로그인 성공" , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Menu.class);
                    intent.putExtra("memberVO", memberVO);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "회원이 아닙니다", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MemberVO> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iv_logo = (ImageView) findViewById(R.id.iv_logo);
        et_id = (EditText) findViewById(R.id.et_id);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_reg = (Button) findViewById(R.id.btn_reg);
        tv_find = (TextView) findViewById(R.id.tv_find);



        setRetrofitInit();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //로그인 메소드(현재는 빌드중이라 임시 주석처리
                login();
//                Intent intent = new Intent(Login.this, Menu.class);
//                startActivity(intent);
            }
        });

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, register.class);
                startActivity(intent);
            }
        });

        tv_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Lost.class);
                startActivity(intent);
            }
        });

    }
}