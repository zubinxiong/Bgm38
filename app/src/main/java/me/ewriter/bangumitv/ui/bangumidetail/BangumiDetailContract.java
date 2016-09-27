package me.ewriter.bangumitv.ui.bangumidetail;

import android.app.Activity;
import android.view.ViewGroup;

import me.drakeet.multitype.Items;
import me.ewriter.bangumitv.api.entity.AnimeDetailEntity;
import me.ewriter.bangumitv.base.BasePresenter;
import me.ewriter.bangumitv.base.BaseView;

/**
 * Created by zubin on 2016/9/24.
 */

public interface BangumiDetailContract {

    interface Presenter extends BasePresenter {
        void requestWebDetail(String subjectId);

        void setUpCover(Activity activity, ViewGroup coverGroup, String imageUrl);

        void clickFab(Activity activity, String bangumiId);

        void updateComment(String mBangumiID, String status, int rating, String comment);

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
