package me.ewriter.bangumitv.ui.persons;

import me.drakeet.multitype.Items;
import me.ewriter.bangumitv.base.BasePresenter;
import me.ewriter.bangumitv.base.BaseView;

/**
 * Created by Zubin on 2016/9/28.
 */

public interface PersonContract {

    interface Presenter extends BasePresenter {
        void requestPerson(String subjectId);
    }

    interface View extends BaseView<Presenter> {
        void setProgressBarVisible(int visible);

        void refresh(Items items);

        void showToast(String msg);
    }
}
