package me.ewriter.bangumitv.utils;

import android.app.Activity;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import me.ewriter.bangumitv.BangumiApp;

/**
 * Created by zubin on 16/7/30.
 */
public class ToastUtils {

    private static Toast toast;

    /**
     * 弹出duration为 LENGTH_SHORT的提示
     * @param context
     * @param msg
     */
    public static void showShortToast(final Activity context, final String msg) {
        if (toast != null)
            toast.cancel();
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }
    }

    /**
     * 弹出duration为 LENGTH_SHORT的提示
     * 需要判断是不是小米？ 部分小米机型 type_toast 没有权限直接崩溃
     *
     * @param context
     * @param resId
     */
    public static void showShortToast(final Activity context, final int resId) {
        if (toast != null)
            toast.cancel();
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }

    }

    /***/
    public static void showShortToast(final int resId) {
        if (toast != null)
            toast.cancel();
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            toast = Toast.makeText(BangumiApp.sAppCtx, resId, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            LogUtil.e(LogUtil.ZUBIN, "这个方法不要在非主线程中使用");
//            context.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    toast = Toast.makeText(BangumiApp.sAppCtx, resId, Toast.LENGTH_SHORT);
//                    toast.show();
//                }
//            });
        }
    }

    public static void showShortToast(String msg) {
        if (toast != null) {
            toast.cancel();
        }

        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            toast = Toast.makeText(BangumiApp.sAppCtx, msg, Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}
