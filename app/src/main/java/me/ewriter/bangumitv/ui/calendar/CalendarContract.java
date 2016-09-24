package me.ewriter.bangumitv.ui.calendar;

import android.app.Activity;

import java.util.List;

import me.ewriter.bangumitv.base.BasePresenter;
import me.ewriter.bangumitv.base.BaseView;
import me.ewriter.bangumitv.dao.BangumiCalendar;

/**
 * Created by Zubin on 2016/9/8.
 *
 */
public interface CalendarContract {

    interface Presenter extends BasePresenter {
        /**打开番剧详情*/
        void openBangumiDetail(Activity activity);

        /**加载数据，从本地数据库缓存和网络数据取，超过6小时的本地缓存会强制网络刷新*/
        void loadData(int position);

        /**下拉刷新时用于强制从网络更新*/
        void forceRefresh();
    }

    interface View extends BaseView<Presenter> {
        /**更新 fragment 当前list 显示*/
        void refresh(List<BangumiCalendar> calendarList);

        void showLoading();

        void hideLoading();

        void showError();
    }
}
