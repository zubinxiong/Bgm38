package me.ewriter.bangumitv.dailyOnAir;

import me.ewriter.bangumitv.utils.LogUtil;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Zubin on 2016/8/31.
 */
public class DailyOnAirPresenter implements DailyOnAirContract.Presenter {

    private DailyOnAirContract.View mDailyView;
    private CompositeSubscription mSubscriptions;

    public DailyOnAirPresenter() {
        mSubscriptions = new CompositeSubscription();
        // 这里把 Presenter 和 View 绑定
        mDailyView.setPresenter(this);
    }

    @Override
    public void loadData() {
        // 这里为了测试 rxjava mvp 先用个测试方法。 实际上是加载每日放送的请求
        Subscription subscription = Observable.just(1, 2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.d(LogUtil.ZUBIN, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(LogUtil.ZUBIN, "onError");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LogUtil.d(LogUtil.ZUBIN, "number = " + integer);
                    }
                });

        mSubscriptions.add(subscription);
    }

    @Override
    public void openBangumiDetail(String something) {

    }

    @Override
    public void subscribe() {
        loadData();
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }
}
