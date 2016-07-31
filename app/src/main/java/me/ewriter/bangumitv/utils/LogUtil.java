package me.ewriter.bangumitv.utils;

import android.util.Log;

import java.lang.reflect.Array;

import me.ewriter.bangumitv.BuildConfig;

/**
 * Created by zubin on 16/6/11.
 */
public class LogUtil {

    private static final String INTERNAL_CMD_START = "xlog@";

    private static boolean XDEBUG = BuildConfig.DEBUG;

    public static final String TAG = "BangumiTV";

    public static final String ZUBIN = "zubin";

    /**
     *  生成log 内容
     * @param obj
     * @return 数组返回数组内容，为空返回null 字符串，其他返回obj
     */
    private static final String genStr(Object obj) {
        String s;
        if (obj == null) {
            s = "null";
        } else {
            Class<? extends Object> clz = obj.getClass();
            if (clz.isArray()) {
                StringBuilder sb = new StringBuilder(clz.getSimpleName());
                sb.append(" [ ");
                int len = Array.getLength(obj);
                for (int i = 0; i < len; i++) {
                    if (i != 0) {
                        sb.append(", ");
                    }
                    Object tmp = Array.get(obj, i);
                    sb.append(tmp);
                }
                sb.append(" ]");
                s = sb.toString();
            } else {
                s = "" + obj;
            }
        }

        return s;
    }


    public static void v(Object obj) {
        if (XDEBUG) {
            Log.v(TAG, genStr(obj));
        }
    }

    public static void d(Object obj) {
        if (XDEBUG) {
            Log.d(TAG, genStr(obj));
        }
    }

    public static void e(Object obj) {
        if (XDEBUG) {
            Log.e(TAG, genStr(obj));
        }
    }

    public static void i(Object obj) {
        if (XDEBUG) {
            Log.i(TAG, genStr(obj));
        }
    }
    //-----------------------------------------------

    public static void v(String tag, Object obj) {
        if (XDEBUG) {
            Log.v(tag, genStr(obj));
        }
    }

    public static void d(String tag, Object obj) {
        if (XDEBUG) {
            Log.d(tag, genStr(obj));
        }
    }

    public static void e(String tag, Object obj) {
        if (XDEBUG) {
            Log.e(tag, genStr(obj));
        }
    }

    public static void i(String tag, Object obj) {
        if (XDEBUG) {
            Log.i(tag, genStr(obj));
        }
    }

    /**
     *  判断是否以内部命令词开头xlog@
     * @param cmd
     * @return
     */
    public static boolean isInternalCmd(String cmd) {
        return (cmd != null && cmd.startsWith(INTERNAL_CMD_START));
    }


    /**
     *  根据输入的内容判断是否打开app内部的log
     *  xlog@open 打开， xlog@close 关闭
     * @param cmd
     */
    public static void switchXLogMode(String cmd) {
        if (cmd != null && !cmd.equals("")) {
            cmd = cmd.replace(INTERNAL_CMD_START, "");
            if ("close".equals(cmd)) {
                openXLog(false);
            } else if ("open".equals(cmd)) {
                openXLog(true);
            }
        }
    }

    /**
     *  设置xdebug 的值，方便在线上app中显示log
     * @param enable
     */
    private static void openXLog(boolean enable) {
        XDEBUG = enable;
        Log.v(TAG, "xlog: " + (XDEBUG ? "open" : "close"));
    }
}
