package me.ewriter.bangumitv.ui.bangumidetail;

import me.ewriter.bangumitv.base.BasePresenter;
import me.ewriter.bangumitv.base.BaseView;

/**
 * Created by zubin on 2016/9/24.
 */

public interface BangumiDetailContract {

    interface Presenter extends BasePresenter {
        void requestWebDetail(String subjectId);
    }


    interface View extends BaseView<Presenter> {

    }
}
