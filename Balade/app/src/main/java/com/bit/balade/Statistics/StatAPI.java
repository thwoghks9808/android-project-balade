package com.bit.balade.Statistics;

import com.bit.balade.Statistics.StatVO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StatAPI {
    @GET("project/call")
    Call<StatVO> getSingleCaseStat(@Query("code") String code, @Query("user_no") String user_no);
    @GET("project/call")
    Call<StatVO> getWeeklyCaseStat(@Query("code") String code, @Query("user_no") String user_no);
    @GET("project/call")
    Call<StatVO> getWeeklyBestStat(@Query("code") String code, @Query("user_no") String user_no);
}
