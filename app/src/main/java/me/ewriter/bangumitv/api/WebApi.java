package me.ewriter.bangumitv.api;

import me.ewriter.bangumitv.api.response.BaseResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Zubin on 2016/9/19.
 */
public interface WebApi {

    /**
     * 获取用户的动画，游戏，图书等状态
     * @param category anime 动画 game 游戏 book 图书
     * @param userId 用户登录后的id
     * @param type wish 想看 collect 看过 do在看 on_hold搁置 dropped 抛弃
     * @param page 页数
     * @return
     */
    @GET("{category}/list/{userId}/{type}")
    Observable<String> listCollection(@Path("category") String category,
                                      @Path("userId") int userId, @Path("type") String type,
                                      @Query("page") int page);

    /**
     * 网页条目搜索
     * @param keyword 关键词
     * @param category 分类，默认为 all 全部 ；1为书籍； 2为动画 ；3为音乐；4为游戏；6为三次元
     * @param page 页数
     * @return
     */
    @GET("subject_search/{keyword}")
    Observable<String> searchItem(@Path("keyword") String keyword,
                                  @Query("cat") String category, @Query("page") int page);


    /**
     * 网页人物搜索
     * @param keyword 关键词
     * @param category 分类， 默认 all，全部；crt 虚拟角色；prsn 现实人物
     * @param page 页数
     * @return
     */
    @GET("mono_search/{keyword}")
    Observable<String> searchPerson(@Path("keyword") String keyword,
                                    @Query("cat") String category, @Query("page") int page);
}
