package me.ewriter.bangumitv.ui.bangumidetail;

import android.app.Activity;
import android.view.ViewGroup;

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
    }


    interface View extends BaseView<Presenter> {
        void refresh(AnimeDetailEntity animeDetailEntity);

        void hideProgress();

        void showToast(String msg);
    }
}
