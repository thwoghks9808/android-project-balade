package com.bit.balade.GpsActivity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceApi {
    @GET("project/insert")
    Call<GpsVo> sendLatLng(@Query("point_lng") String point_lng, @Query("point_lat") String point_lat);
    @GET("project/start")
    Call<GpsVo> sendStart(@Query("user_no") String user_no);
    @GET("project/end")
    Call<GpsVo> sendEnd(@Query("user_no") String user_no, @Query("distance") String distance);
}
