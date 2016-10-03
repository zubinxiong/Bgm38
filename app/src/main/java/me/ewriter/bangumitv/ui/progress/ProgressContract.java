package me.ewriter.bangumitv.ui.progress;

import java.util.List;

import me.drakeet.multitype.Items;
import me.ewriter.bangumitv.api.entity.AnimeEpEntity;
import me.ewriter.bangumitv.base.BasePresenter;
import me.ewriter.bangumitv.base.BaseView;

/**
 * Created by Zubin on 2016/9/28.
 */

public interface ProgressContract {

    interface Presenter extends BasePresenter {
        void requestProgress(String subjectId);

        void updateEpStatus(AnimeEpEntity entity, int position, int gridPosition);
    }

    interface View extends BaseView<Presenter> {
        void setProgressBarVisible(int visible);

        void refresh(List<AnimeEpEntity> items);

        void showToast(String msg);

        void dismissProgressDialog();

        void showProgressDialog();

        void updateEp(int position);

    }

}
