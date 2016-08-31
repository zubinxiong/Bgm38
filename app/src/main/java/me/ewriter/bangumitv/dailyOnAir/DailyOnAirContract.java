package me.ewriter.bangumitv.dailyOnAir;

import me.ewriter.bangumitv.base.BasePresenter;
import me.ewriter.bangumitv.base.BaseView;

/**
 * Created by Zubin on 2016/8/31.
 */
public interface DailyOnAirContract {

    interface Presenter extends BasePresenter {
        void loadData();

        void openBangumiDetail(String something);
    }

    interface View extends BaseView<Presenter> {
        void showRefreshView();

        void setTitle();

        void stopRefreshView();
    }
}
