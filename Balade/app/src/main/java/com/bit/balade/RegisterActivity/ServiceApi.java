package com.bit.balade.RegisterActivity;
import com.bit.balade.RegisterActivity.JoinVO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceApi {
    @GET("project/join")
    Call<JoinVO> userJoin(@Query("user_id") String user_id, @Query("user_pwd") String user_pwd, @Query("user_email") String user_email);
}
