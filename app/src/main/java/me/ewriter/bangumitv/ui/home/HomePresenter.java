package me.ewriter.bangumitv.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDelegate;

import me.ewriter.bangumitv.BangumiApp;
import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.api.BangumiApi;
import me.ewriter.bangumitv.api.LoginManager;
import me.ewriter.bangumitv.constants.MyConstants;
import me.ewriter.bangumitv.event.OpenNavigationEvent;
import me.ewriter.bangumitv.ui.about.AboutActivity;
import me.ewriter.bangumitv.ui.calendar.CalendarFragment;
import me.ewriter.bangumitv.ui.collection.CollectionFragment;
import me.ewriter.bangumitv.ui.login.LoginActivity;
import me.ewriter.bangumitv.ui.search.SearchActivity;
import me.ewriter.bangumitv.utils.LogUtil;
import me.ewriter.bangumitv.utils.PreferencesUtils;
import me.ewriter.bangumitv.utils.RxBus;
import me.ewriter.bangumitv.utils.ToastUtils;
import rx.Observer;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Zubin on 2016/9/18.
 */
public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mHomeView;
    private CompositeSubscription mSubscriptions;
    private Subscription mRxOpenSub;

    public HomePresenter(HomeContract.View mHomeView) {
        this.mHomeView = mHomeView;
        mHomeView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        mSubscriptions = new CompositeSubscription();

        subscribeOpenDrawerEvent();
    }

    private void subscribeOpenDrawerEvent() {
        if (mRxOpenSub != null) {
            mSubscriptions.remove(mRxOpenSub);
        }

        mRxOpenSub = RxBus.getDefault().toObservable(OpenNavigationEvent.class)
                .subscribe(new Observer<OpenNavigationEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        // 如果异常，这里后面就收不到event了，必须重新订阅
                        LogUtil.d(LogUtil.ZUBIN, e.getMessage());
                        subscribeOpenDrawerEvent();
                    }

                    @Override
                    public void onNext(OpenNavigationEvent openNavigationEvent) {
                        LogUtil.d(LogUtil.ZUBIN, "OpenNavigationEvent onNext");
                        mHomeView.openDrawer();
                    }
                });

        mSubscriptions.add(mRxOpenSub);
    }

    @Override
    public void unsubscribe() {
        if (mRxOpenSub != null) {
            mSubscriptions.remove(mRxOpenSub);
        }
        RxBus.getDefault().removeAllStickyEvents();
    }

    @Override
    public void updateCurrentTag(String tag) {
        PreferencesUtils.putString(BangumiApp.sAppCtx, "current", tag);
    }

    @Override
    public String getCurrentTag() {
        return PreferencesUtils.getString(BangumiApp.sAppCtx, "current", CalendarFragment.class.getName());
    }

    @Override
    public void updateSelectedFragment(FragmentManager manager, String tag) {

        Fragment f = manager.findFragmentByTag(tag);
        if (f == null) {
            if (tag.equals(CalendarFragment.class.getName())) {
                manager.beginTransaction()
                        .replace(R.id.frame_container, CalendarFragment.newInstance(), tag)
                        .commit();
            } else {
                manager.beginTransaction()
                        .replace(R.id.frame_container, CollectionFragment.newInstance(), tag)
                        .commit();
                mHomeView.updateDrawChecked(R.id.nav_collections);
            }
        }
    }

    @Override
    public void openSearch(Activity context) {
        context.startActivity(new Intent(context, SearchActivity.class));
    }

    @Override
    public void openExpand(Activity context) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(BangumiApp.sAppCtx.getResources().getColor(R.color.colorPrimary));
        builder.setShowTitle(true);
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(context, Uri.parse(BangumiApi.ULTRA_EXPAND_URL));
    }

    @Override
    public void openAbout(Activity activity) {
        activity.startActivity(new Intent(activity, AboutActivity.class));
    }

    @Override
    public void logout() {
        if (LoginManager.isLogin(BangumiApp.sAppCtx)) {
            mHomeView.showLogoutDialog();
        } else {
            ToastUtils.showShortToast(R.string.not_login_hint);
        }
    }

    @Override
    public void login(Activity activity) {
        if (LoginManager.isLogin(BangumiApp.sAppCtx)) {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setToolbarColor(BangumiApp.sAppCtx.getResources().getColor(R.color.colorPrimary));
            builder.setShowTitle(true);
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(activity, Uri.parse(LoginManager.getUserHomeUrl(BangumiApp.sAppCtx)));
        } else {
            activity.startActivity(new Intent(activity, LoginActivity.class));
        }
    }

    @SuppressWarnings("WrongConstant")
    @Override
    public void updateTheme(boolean isChecked, Activity activity) {
        PreferencesUtils.putBoolean(BangumiApp.sAppCtx, MyConstants.THEME_KEY, isChecked);
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        activity.recreate();
    }

    @Override
    public void checkLogin() {
        if (LoginManager.isLogin(BangumiApp.sAppCtx)) {
            mHomeView.setUserInfo();
        }
    }

}
