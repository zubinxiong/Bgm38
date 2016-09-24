package me.ewriter.bangumitv.ui.home;

import android.content.DialogInterface;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SwitchCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import me.ewriter.bangumitv.BangumiApp;
import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.api.LoginManager;
import me.ewriter.bangumitv.base.BaseActivity;
import me.ewriter.bangumitv.constants.MyConstants;
import me.ewriter.bangumitv.event.UserLogoutEvent;
import me.ewriter.bangumitv.ui.calendar.CalendarFragment;
import me.ewriter.bangumitv.ui.collection.CollectionFragment;
import me.ewriter.bangumitv.utils.LogUtil;
import me.ewriter.bangumitv.utils.PreferencesUtils;
import me.ewriter.bangumitv.utils.RxBus;
import rx.Subscription;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, HomeContract.View, View.OnClickListener {

    private DrawerLayout mDrawLayout;
    private NavigationView mNavigationView;
    private ImageView mNavLogout;
    private CircleImageView mAvatar;
    private TextView mUserName;
    private TextView mSign;
    private SwitchCompat mSwitch;
    private FragmentManager mFragmentManager;
    private HomeContract.Presenter mPresenter;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_main;
    }

    protected void initView() {

        mPresenter = new HomePresenter(this);
        mPresenter.subscribe();

        mDrawLayout = (DrawerLayout) findViewById(R.id.drawer);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        mNavLogout = (ImageView) mNavigationView.getHeaderView(0).findViewById(R.id.nav_logout);
        mAvatar = (CircleImageView) mNavigationView.getHeaderView(0).findViewById(R.id.profile_image);
        mUserName = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.username);
        mSign = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.sign);
        mSwitch = (SwitchCompat) mNavigationView.getMenu().getItem(4).getActionView();
        mFragmentManager = getSupportFragmentManager();

        mNavLogout.setOnClickListener(this);
        mAvatar.setOnClickListener(this);
        mSwitch.setOnClickListener(this);
        mNavigationView.setNavigationItemSelectedListener(this);

        // 选中那个标签
        String current = mPresenter.getCurrentTag();
        mPresenter.updateSelectedFragment(mFragmentManager, current);
        updateSwitcher();

    }

    @SuppressWarnings("WrongConstant")
    @Override
    protected void initBefore() {

    }

    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {

        closeDrawer();
        mDrawLayout.post(new Runnable() {
            @Override
            public void run() {
                switch (item.getItemId()) {
                    case R.id.nav_daily:
                        mPresenter.updateSelectedFragment(mFragmentManager, CalendarFragment.class.getName());
                        mPresenter.updateCurrentTag(CalendarFragment.class.getName());
                        break;

                    case R.id.nav_collections:
                        mPresenter.updateSelectedFragment(mFragmentManager, CollectionFragment.class.getName());
                        mPresenter.updateCurrentTag(CollectionFragment.class.getName());
                        break;

                    case R.id.nav_search:
                        mPresenter.openSearch(MainActivity.this);
                        break;

                    case R.id.nav_ultra_expand:
                        mPresenter.openExpand(MainActivity.this);
                        break;

                    case R.id.nav_about:
                        mPresenter.openAbout(MainActivity.this);
                        break;

                    default:
                        break;
                }
            }
        });

        return true;

    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void updateDrawChecked(int id) {
        mNavigationView.setCheckedItem(id);
    }

    @Override
    public void closeDrawer() {
        if (mDrawLayout != null && mDrawLayout.isShown()) {
            mDrawLayout.closeDrawers();
        }
    }

    @Override
    public void openDrawer() {
        mDrawLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void showLogoutDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.logout_title));
        builder.setMessage(getString(R.string.logout_content));
        builder.setPositiveButton(getString(R.string.logout_confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoginManager.logout(MainActivity.this);
//                EventBus.getDefault().post(new LogoutEvent());
                // TODO: 2016/9/23 普通的event只能覆盖到当前已经初始化的，比如在最后一个tab，那么久无法清除前面几个数据
//                RxBus.getDefault().post(new UserLogoutEvent());
                RxBus.getDefault().postSticky(new UserLogoutEvent());
                clearLoginInfo();
                dialog.dismiss();
                closeDrawer();
            }
        });

        builder.setNegativeButton(getString(R.string.logout_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    @Override
    public void updateSwitcher() {
        boolean isChecked = PreferencesUtils.getBoolean(BangumiApp.sAppCtx, MyConstants.THEME_KEY, false);
        mSwitch.setChecked(isChecked);
    }

    @Override
    public void setUserInfo() {
        mUserName.setText(LoginManager.getUserNickName(this));
        mSign.setText(LoginManager.getSign(this));
        Picasso.with(this).load(LoginManager.getLargeAvatar(this)).noFade().into(mAvatar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_logout:
                mPresenter.logout();
                break;

            case R.id.profile_image:
                mPresenter.login(MainActivity.this);
                break;

            case R.id.night_mode:
                mPresenter.updateTheme(mSwitch.isChecked(), MainActivity.this);
                LogUtil.d(LogUtil.ZUBIN, "night clicked + " + mSwitch.isChecked());
                break;

            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.checkLogin();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    private void clearLoginInfo() {
        mUserName.setText("");
        mSign.setText("");
        Picasso.with(this).load(R.drawable.ic_drawer_login_default).noFade().into(mAvatar);
    }
}
