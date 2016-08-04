package me.ewriter.bangumitv.api;


import java.util.List;

import me.ewriter.bangumitv.api.response.BangumiDetail;
import me.ewriter.bangumitv.api.response.BaseResponse;
import me.ewriter.bangumitv.api.response.Calendar;
import me.ewriter.bangumitv.api.response.MyCollection;
import me.ewriter.bangumitv.api.response.Token;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Zubin on 2016/7/25.
 */
public interface BangumiApi {

    /** 超展开 url 地址，此处可以使用 jsoup 抓取数据*/
    public static final String ULTRA_EXPAND_URL = "http://bangumi.tv/m";

    /**
     *  login the www.bangumi.tv
     * @param username  user's email
     * @param password  user's password
     * @return LoginInformation, Now this only return BaseResponse for test
     */
    @FormUrlEncoded
    @POST("auth?source=onAir")
    public Call<Token> login(@Field("username") String username,
                             @Field("password") String password);

    /**
     *  获取一个番剧的基本信息
     * @param subjectId 番剧的id
     * @return
     */
    @GET("subject/{subjectId}")
    public Call<BaseResponse> getSubject(@Path("subjectId") int subjectId);

    /**
     * 获取某个番剧的我的评论(状态，评分，吐槽信息)
     * @param subjectId 番剧id
     * @param auth 登录成功后返回的 auth 信息
     * @return
     */
    @GET("collection/{subjectId}?source=onAir")
    public Call<BaseResponse> getSubjectCollection(@Path("subjectId") int subjectId,
                                                   @Query("auth") String auth);


    /**
     * 获取番剧的详细信息，比如 staff，eps(每集的信息) 等等
     * @param subjectId 番剧 id
     * @return
     */
    @GET("subject/{subjectId}?responseGroup=large")
    public Call<BangumiDetail> getBangumiDetail(@Path("subjectId") int subjectId);

    /**
     *  获取番剧的每集状态，比如第一集看过，第二季抛弃，想看等状态
     * @param userId 登录后返回的用户id
     * @param auth 登录成功后的 auth 信息
     * @param subject_id 番剧 id
     * @return
     */
    @GET("user/{userId}/progress?source=onAir")
    public Call<BaseResponse> getSubjectProgress(@Path("userId") int userId,
                                                 @Query("auth") String auth,
                                                 @Query("subject_id") int subject_id);

    /**
     * 获取用户当前正在追的番 (我的进度)
     * @param userId
     * @return
     */
    @GET("user/{userId}/collection?cat=watching")
    public Call<List<MyCollection>> getUserCollection(@Path("userId") int userId);

    /**
     *  获取周一到周五的每日放送
     * @return
     */
    @GET("calendar")
    public Call<List<Calendar>> listCalendar();

    /***
     *  搜索番剧
     * @param query 番剧的名称
     * @return
     */
    @GET("search/subject/{query}")
    public Call<BaseResponse> search(@Path("query") String query);

    /**
     * 更新某个番剧的评价
     * @param subjectId 番剧id
     * @param status 番剧当前的状态(想看，看过，在看，搁置，弃番)
     * @param rating 评分 0-10
     * @param comment 吐槽
     * @param auth 登录的auth字段
     * @return
     */
    @FormUrlEncoded
    @POST("collection/{subjectId}/update?source=onAir")
    public Call<BaseResponse> updateCollection(@Path("subjectId") int subjectId,
                                               @Field("status") String status,
                                               @Field("rating") int rating,
                                               @Field("comment") String comment,
                                               @Query("auth") String auth);

    /**
     * 更新每一集的观看状态, 是否要加一个标记之前所有为看过的方法？
     * @param epId 每一集的id
     * @param status 观看的状态包括 看过(watched)，想看(queue)，撤销(remove)，抛弃(drop)
     * @param auth 登录后返回的 auth 字段
     * @return
     */
    @FormUrlEncoded
    @POST("ep/{epId}/status/{status}?source=onAir")
    public Call<BaseResponse> updateEp(@Path("epId") int epId,
                                       @Path("status") String status,
                                       @Field("auth") String auth);
}
