package com.bit.balade.LostActivity;

import retrofit2.Call;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceApi {
    @GET("project-0/findid")
    Call<FindVO> findUserID(@Query("user_email") String user_email);

    @GET("project/findpwd")
    Call<FindVO> findUserPassword(@Query("user_id") String user_id);
}
