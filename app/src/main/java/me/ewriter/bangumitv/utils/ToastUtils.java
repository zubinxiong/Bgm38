package me.ewriter.bangumitv.utils;

import android.content.Context;
import android.widget.Toast;

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
    public static void showShortToast(Context context, String msg) {
        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * 弹出duration为 LENGTH_SHORT的提示
     *
     * @param context
     * @param resId
     */
    public static void showShortToast(Context context, int resId) {
        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        toast.show();
    }
}
