package com.bit.balade.LoginActivity;
import com.bit.balade.LoginActivity.MemberVO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LoginService {
    @GET("project/login/")
    Call<MemberVO> getMember(@Query("user_id") String user_id, @Query("user_pwd") String user_pwd);
}
