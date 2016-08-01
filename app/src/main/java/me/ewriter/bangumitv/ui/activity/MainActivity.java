package me.ewriter.bangumitv.ui.activity;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import org.greenrobot.eventbus.Subscribe;

import me.ewriter.bangumitv.BangumiApp;
import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.base.BaseActivity;
import me.ewriter.bangumitv.constants.MyConstants;
import me.ewriter.bangumitv.event.OpenNavgationEvent;
import me.ewriter.bangumitv.ui.fragment.CalendarFragment;
import me.ewriter.bangumitv.ui.fragment.MyCollectionFragment;
import me.ewriter.bangumitv.utils.ToastUtils;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    DrawerLayout mDrawLayout;
    NavigationView mNavigationView;
    ImageView mNavLogout;
    FragmentManager mFragmentManager;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        mDrawLayout = (DrawerLayout) findViewById(R.id.drawer);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        mNavLogout = (ImageView) mNavigationView.getHeaderView(0).findViewById(R.id.nav_logout);
        mNavLogout.setOnClickListener(this);
        mNavigationView.setNavigationItemSelectedListener(this);
        mFragmentManager = getSupportFragmentManager();

        mFragmentManager.beginTransaction()
                .add(R.id.frame_container , new CalendarFragment(), MyConstants.TAG_CALENDER_FRAGMENT)
                .commit();

    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected boolean isSubscribeEvent() {
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        item.setChecked(item.isChecked());
        mDrawLayout.closeDrawers();
        switch (item.getItemId()) {
            case R.id.nav_daily:
                startFragment(MyConstants.TAG_CALENDER_FRAGMENT);
                return true;

            case R.id.nav_collections:
                startFragment(MyConstants.TAG_MY_COLLECTION);
                return true;

            case R.id.nav_search:
                ToastUtils.showShortToast(BangumiApp.sAppCtx, R.string.nav_search);
                return true;

            case R.id.nav_ultra_expand:
                ToastUtils.showShortToast(BangumiApp.sAppCtx, R.string.nav_ultra_expand);
                return true;

            case R.id.nav_about:
                ToastUtils.showShortToast(BangumiApp.sAppCtx, R.string.nav_about);
                return true;

            case R.id.nav_setting:
                ToastUtils.showShortToast(BangumiApp.sAppCtx, R.string.nav_setting);
                return true;

            default:
                return true;
        }
    }

    private void startFragment(String tag) {
        Fragment target = mFragmentManager.findFragmentByTag(tag);

        if (target == null) {
            if (tag.equals(MyConstants.TAG_CALENDER_FRAGMENT)) {
                target = new CalendarFragment();
            } else if (tag.equals(MyConstants.TAG_MY_COLLECTION)){
                target = new MyCollectionFragment();
            }
            mFragmentManager.beginTransaction()
                    .replace(R.id.frame_container, target, tag)
//                    .addToBackStack(tag)
                    .commit();

        } else {
            mFragmentManager.popBackStack();
        }
    }

    @Override
    public void onBackPressed() {
        if (mNavigationView.isShown()) {
            mDrawLayout.closeDrawers();
            return;
        }

        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nav_logout:
                ToastUtils.showShortToast(BangumiApp.sAppCtx, R.string.nav_logout);
                break;
        }
    }

    @Subscribe
    public void openNavigationEvent(OpenNavgationEvent event) {
        mDrawLayout.openDrawer(GravityCompat.START);
    }
}
