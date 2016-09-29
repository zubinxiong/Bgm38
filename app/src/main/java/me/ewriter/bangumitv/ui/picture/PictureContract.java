package me.ewriter.bangumitv.ui.picture;

import me.ewriter.bangumitv.base.BasePresenter;
import me.ewriter.bangumitv.base.BaseView;

/**
 * Created by Zubin on 2016/9/29.
 */

public interface PictureContract {

    interface Presenter extends BasePresenter {
        void checkPermission(String imageUrl, String name);

        void downLoadImage(String imageUrl, String name);
    }

    interface View extends BaseView<Presenter> {
        void showToast(String msg);
    }
}
