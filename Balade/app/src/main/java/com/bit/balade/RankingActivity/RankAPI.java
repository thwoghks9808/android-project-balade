package com.bit.balade.RankingActivity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RankAPI {
    @GET("project/ranking")
    Call<RankVO> getRankPage(@Query("user_id") String user_id);
}
