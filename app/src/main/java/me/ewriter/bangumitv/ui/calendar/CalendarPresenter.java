package me.ewriter.bangumitv.ui.calendar;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import me.ewriter.bangumitv.BangumiApp;
import me.ewriter.bangumitv.api.ApiManager;
import me.ewriter.bangumitv.api.response.DailyCalendar;
import me.ewriter.bangumitv.constants.MyConstants;
import me.ewriter.bangumitv.dao.BangumiCalendar;
import me.ewriter.bangumitv.dao.BangumiCalendarDao;
import me.ewriter.bangumitv.dao.DaoSession;
import me.ewriter.bangumitv.utils.LogUtil;
import me.ewriter.bangumitv.utils.PreferencesUtils;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Zubin on 2016/9/8.
 */
public class CalendarPresenter implements CalendarContract.Presenter {

    private CalendarContract.View mCalendarView;
    private CompositeSubscription mSubscriptions;

    public CalendarPresenter(CalendarContract.View mCalendarView) {
        this.mCalendarView = mCalendarView;

        // 绑定 Presenter 和 View
        mCalendarView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        // do some rx task
        mSubscriptions = new CompositeSubscription();
//        requestData();
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void openBangumiDetail() {

    }

    @Override
    public void loadData(final int position) {
        // 数据库缓存
        Observable<List<BangumiCalendar>> cache = Observable.create(new Observable.OnSubscribe<List<BangumiCalendar>>() {
            @Override
            public void call(Subscriber<? super List<BangumiCalendar>> subscriber) {
                try {
                    subscriber.onStart();
                    // 查询
                    List<BangumiCalendar> items = new ArrayList<BangumiCalendar>();

                    long deta = System.currentTimeMillis() - PreferencesUtils.getLong(BangumiApp.sAppCtx,
                            MyConstants.CALENDAR_REFRESH_KEY, 0);
                    // 每间隔6小时强制使用网络数据一次
                    if (deta < MyConstants.CALENDAR_REFRESH_TIME) {
                        items.addAll(BangumiApp.sAppCtx.getDaoSession().getBangumiCalendarDao()
                                .queryBuilder().where(BangumiCalendarDao.Properties.Air_weekday.eq(position + 1))
                                .build().list());
                    }
                    LogUtil.d(LogUtil.ZUBIN, "thread = " + Thread.currentThread() + "length = " + items.size());
                    subscriber.onNext(items);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });

        // 网络数据
        Observable<List<BangumiCalendar>> network = ApiManager.getBangumiInstance().listCalendar()
                .map(new Func1<List<DailyCalendar>, List<BangumiCalendar>>() {
                    @Override
                    public List<BangumiCalendar> call(List<DailyCalendar> calendarList) {
                        return saveToDbData(calendarList, position);
                    }
                });

        // concat 两个数据，哪个不为空用哪个，优先取 cache
        Observable<List<BangumiCalendar>> source = Observable.concat(cache, network)
                .first(new Func1<List<BangumiCalendar>, Boolean>() {
                    @Override
                    public Boolean call(List<BangumiCalendar> bangumiCalendars) {
                        return (bangumiCalendars != null && bangumiCalendars.size() != 0);
                    }
                });

        Subscription subscription = source.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<BangumiCalendar>>() {
                    @Override
                    public void onCompleted() {
                        mCalendarView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(LogUtil.ZUBIN, "onError " + e.getMessage());
                        mCalendarView.showError();
                        mCalendarView.hideLoading();
                    }

                    @Override
                    public void onNext(List<BangumiCalendar> bangumiCalendars) {
                        LogUtil.d(LogUtil.ZUBIN, "onNext ");
                        mCalendarView.refresh(bangumiCalendars);

                    }
                });

        mSubscriptions.add(subscription);
    }

    @Override
    public void forceRefresh() {
        PreferencesUtils.putLong(BangumiApp.sAppCtx, MyConstants.CALENDAR_REFRESH_KEY, 0);
    }


    /** 将网络请求的数据存储到数据库并将数据全部返回*/
    private List<BangumiCalendar> saveToDbData(List<DailyCalendar> mList, int position) {
        Log.d(LogUtil.ZUBIN, "save Thread = " + Thread.currentThread());
        DaoSession daoSession = BangumiApp.sAppCtx.getDaoSession();

        // 存到数据库中的所有数据
        List<BangumiCalendar> mDbList = new ArrayList<>();
        // 返回的周一的数据
        List<BangumiCalendar> mReturenList = new ArrayList<>();

        for (int i = 0; i < mList.size(); i++) {
            DailyCalendar calendar = mList.get(i);
            for (int j = 0; j < calendar.getItems().size(); j++) {
                BangumiCalendar entity = new BangumiCalendar();

                entity.setAir_weekday(calendar.getItems().get(j).getAir_weekday());
                entity.setName_cn(calendar.getItems().get(j).getName_cn());
                entity.setBangumi_id(calendar.getItems().get(j).getId());
                entity.setBangumi_total(calendar.getItems().get(j).getRating().getTotal());
                entity.setBangumi_average(calendar.getItems().get(j).getRating().getScore());
                if (calendar.getItems().get(j).getImages() != null) {
                    entity.setCommon_image(calendar.getItems().get(j).getImages().getCommon());
                    entity.setLarge_image(calendar.getItems().get(j).getImages().getLarge());
                    entity.setMedium_image(calendar.getItems().get(j).getImages().getMedium());
                    entity.setSmall_image(calendar.getItems().get(j).getImages().getSmall());
                    entity.setGrid_image(calendar.getItems().get(j).getImages().getGrid());
                }

                entity.setName_jp(calendar.getItems().get(j).getName());
                entity.setRank(calendar.getItems().get(j).getRank());

                mDbList.add(entity);

                if (calendar.getItems().get(j).getAir_weekday() == (position + 1)) {
                    mReturenList.add(entity);
                }
            }
        }
        daoSession.getBangumiCalendarDao().deleteAll();
        daoSession.getBangumiCalendarDao().insertInTx(mDbList);

        // 保存更新时间
        PreferencesUtils.putLong(BangumiApp.sAppCtx, MyConstants.CALENDAR_REFRESH_KEY, System.currentTimeMillis());

        return mReturenList;
    }
}
