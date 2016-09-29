package me.ewriter.bangumitv.ui.bangumidetail;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ImageView;

import me.drakeet.multitype.Items;
import me.ewriter.bangumitv.api.entity.AnimeDetailEntity;
import me.ewriter.bangumitv.base.BasePresenter;
import me.ewriter.bangumitv.base.BaseView;

/**
 * Created by zubin on 2016/9/24.
 */

public interface BangumiDetailContract {

    interface Presenter extends BasePresenter {
        /** zip 请求网页概览及评论 */
        void requestWebDetail(String subjectId);

        /** 处理封面的模糊效果*/
        void setUpCover(Activity activity, ViewGroup coverGroup, String imageUrl);

        /**  处理fab 点击的弹出效果 */
        void clickFab(Activity activity, String bangumiId);

        void clickCoverImage(Activity activity, String imageUrl, String name, ImageView view);

        /** 提交用户的评价 */
        void updateComment(String mBangumiID, String status, int rating, String comment);

        /** 分享*/
        void shareDetail(String bangumid, String name, Activity activity);

    }


    interface View extends BaseView<Presenter> {
        void refresh(Items items);

        void hideProgress();

        void showToast(String msg);

        void updateHeader(String summary, String tag, String score);

        void setFabVisible(int visible);

        void showEvaluationDialog();

        void insertComment(String comment, int status, int rating);
    }
}
