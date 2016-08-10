package me.ewriter.bangumitv.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import de.hdodenhof.circleimageview.CircleImageView;
import me.ewriter.bangumitv.BangumiApp;
import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.api.BangumiApi;
import me.ewriter.bangumitv.api.LoginManager;
import me.ewriter.bangumitv.base.BaseActivity;
import me.ewriter.bangumitv.constants.MyConstants;
import me.ewriter.bangumitv.event.LogoutEvent;
import me.ewriter.bangumitv.event.OpenNavgationEvent;
import me.ewriter.bangumitv.ui.fragment.CalendarFragment;
import me.ewriter.bangumitv.ui.fragment.MyCollectionFragment;
import me.ewriter.bangumitv.utils.LogUtil;
import me.ewriter.bangumitv.utils.ToastUtils;
import me.ewriter.bangumitv.utils.Tools;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    DrawerLayout mDrawLayout;
    NavigationView mNavigationView;
    ImageView mNavLogout;
    FragmentManager mFragmentManager;
    CircleImageView mAvatar;
    TextView mUserName;
    TextView mSign;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        mDrawLayout = (DrawerLayout) findViewById(R.id.drawer);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        mNavLogout = (ImageView) mNavigationView.getHeaderView(0).findViewById(R.id.nav_logout);
        mAvatar = (CircleImageView) mNavigationView.getHeaderView(0).findViewById(R.id.profile_image);
        mUserName = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.username);
        mSign = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.sign);
        mNavLogout.setOnClickListener(this);
        mAvatar.setOnClickListener(this);
        mNavigationView.setNavigationItemSelectedListener(this);
        mFragmentManager = getSupportFragmentManager();

        mFragmentManager.beginTransaction()
                .add(R.id.frame_container , new CalendarFragment(), MyConstants.TAG_CALENDER_FRAGMENT)
                .commit();

    }

    @Override
    protected void initBeforeCreate() {

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
                startActivity(new Intent(this, SearchActivity.class));
                return true;

            case R.id.nav_ultra_expand:
                LogUtil.d(LogUtil.ZUBIN, "is chrome installed? = " + Tools.isChromeCustomTabsSupported(this));
                mDrawLayout.closeDrawers();
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(this, Uri.parse(BangumiApi.ULTRA_EXPAND_URL));
                return true;

            case R.id.nav_about:
//                ToastUtils.showShortToast(BangumiApp.sAppCtx, R.string.nav_about);
                startActivity(new Intent(this, AboutActivity.class));
                return true;

//            case R.id.nav_setting:
//                ToastUtils.showShortToast(BangumiApp.sAppCtx, R.string.nav_setting);
//                return true;

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
                if (LoginManager.isLogin(MainActivity.this)) {
                    showLogoutDialog();
                } else {
                    ToastUtils.showShortToast(MainActivity.this, R.string.not_login_hint);
                }
                break;

            case R.id.profile_image:
                if (LoginManager.isLogin(MainActivity.this)) {
                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                    CustomTabsIntent customTabsIntent = builder.build();
                    customTabsIntent.launchUrl(this, Uri.parse(LoginManager.getUserHomeUrl(this)));
                } else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
                break;

            default:
                break;
        }
    }

    private void showLogoutDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.logout_title));
        builder.setMessage(getString(R.string.logout_content));
        builder.setPositiveButton(getString(R.string.logout_confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoginManager.logout(MainActivity.this);
                EventBus.getDefault().post(new LogoutEvent());
                dialog.dismiss();
                mDrawLayout.closeDrawers();
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

    @Subscribe
    public void openNavigationEvent(OpenNavgationEvent event) {
        mDrawLayout.openDrawer(GravityCompat.START);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d(LogUtil.ZUBIN, "onresume main");
        if (LoginManager.isLogin(this)) {
            mUserName.setText(LoginManager.getUserNickName(this));
            mSign.setText(LoginManager.getSign(this));
            Picasso.with(this).load(LoginManager.getLargeAvatar(this)).noFade().into(mAvatar);
        }
    }

    @Subscribe
    public void onLogout(LogoutEvent event) {
        mUserName.setText("");
        mSign.setText("");
        Picasso.with(this).load(R.drawable.ic_drawer_login_default).noFade().into(mAvatar);
    }
}
