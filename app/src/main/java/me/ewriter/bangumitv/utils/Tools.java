package me.ewriter.bangumitv.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsServiceConnection;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Zubin on 2016/8/5.
 * 一些常用的方法，不好归类，统一放这里
 */
public class Tools {

    /**
     * Check if Chrome CustomTabs are supported.
     * Some devices don't have Chrome or it may not be
     * updated to a version where custom tabs is supported.
     *
     * @param context the context
     * @return whether custom tabs are supported
     */
    public static boolean isChromeCustomTabsSupported(@NonNull final Context context) {
        Intent serviceIntent = new Intent("android.support.customtabs.action.CustomTabsService");
        serviceIntent.setPackage("com.android.chrome");

        CustomTabsServiceConnection serviceConnection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(final ComponentName componentName, final CustomTabsClient customTabsClient) { }

            @Override
            public void onServiceDisconnected(final ComponentName name) { }
        };

        boolean customTabsSupported =
                context.bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE | Context.BIND_WAIVE_PRIORITY);
        context.unbindService(serviceConnection);

        return customTabsSupported;
    }

    /**
     * 收起软键盘
     */
    public static void hideInputMethod(Activity activity){
        if(activity != null && activity.getCurrentFocus() != null){
            InputMethodManager input = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /** 显示虚拟键盘 */
    public static void showInputMethod(View v)
    {
        InputMethodManager imm = ( InputMethodManager ) v.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );
        if(!imm.isActive())
            imm.showSoftInput(v,InputMethodManager.SHOW_FORCED);
    }

    /**
     * 获取当前应用版本号
     * @return
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }
}
