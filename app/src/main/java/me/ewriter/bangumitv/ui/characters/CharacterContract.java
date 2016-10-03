package me.ewriter.bangumitv.ui.characters;

import me.drakeet.multitype.Items;
import me.ewriter.bangumitv.base.BasePresenter;
import me.ewriter.bangumitv.base.BaseView;

/**
 * Created by Zubin on 2016/9/28.
 */

public interface CharacterContract {

    interface Presenter extends BasePresenter {
        void requestCharacter(String subjectId);
    }


    interface View extends BaseView<Presenter> {
        void refresh(Items items);

        void showToast(String msg);

        void setProgressBarVisible(int visible);
    }
}
