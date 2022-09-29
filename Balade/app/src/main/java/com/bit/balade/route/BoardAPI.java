package com.bit.balade.route;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BoardAPI {
    @GET("project/post")
    Call<PostVo> sendPostArticle(@Query("tag") String tag, @Query("title") String title,
                                 @Query("content") String post_content, @Query("user_no") String user_no);

    @GET("project/list")
    Call<PostVo> createArticlePage(@Query("user_no") String user_no);

    @GET("project/delete")
    Call<PostVo> deleteArticle(@Query("no") String no);

    @GET("project/reply")
    Call<PostVo> addReply(@Query("no") String no, @Query("user_id") String user_id, @Query("content") String content);

    @GET("project/content")
    Call<PostVo> getReply(@Query("no") String no);

    @GET("project/delreply")
    Call<PostVo> delReply(@Query("no") String no, @Query("user_id") String user_id, @Query("content") String content);
}
