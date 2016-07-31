package me.ewriter.bangumitv.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Zubin on 2016/4/15.
 */
public class PreferencesUtils {

    public static String DEFAULT_PREFENCE_NAME = "BangumiTV";

    /**
     *  存入默认的 preferences = BangumiTV 中
     * @param context
     * @param key
     * @param value
     * @return 如果成功后返回true
     */
    public static boolean putString(Context context, String key, String value) {
        return putString(context, DEFAULT_PREFENCE_NAME, key, value);
    }

    /***
     *
     * @param context
     * @param preferencesName  指定的preferences 名称
     * @param key
     * @param value
     * @return
     */
    public static boolean putString(Context context, String preferencesName, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /** 获取默认preferences = MyBangumi 中的String*/
    public static String getString(Context context, String key, String defaultValue) {
        return getString(context, DEFAULT_PREFENCE_NAME, key, defaultValue);
    }

    /** 获取指定preferences 中的String*/
    public static String getString(Context context, String preferencesName, String key, String defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }

    public static boolean putInt(Context context, String key, int value) {
        return putInt(context, DEFAULT_PREFENCE_NAME, key, value);
    }

    public static boolean putInt(Context context, String preferencesName, String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    /** 获取默认preferences = MyBangumi 中的int */
    public static int getInt(Context context, String key, int defaultValue) {
        return getInt(context, DEFAULT_PREFENCE_NAME, key, defaultValue);
    }

    /** 获取指定preferences 中的int */
    public static int getInt(Context context, String preferencesName, String key, int defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
        return settings.getInt(key, defaultValue);
    }

    public static boolean putLong(Context context, String key, long value) {
        return putLong(context, DEFAULT_PREFENCE_NAME, key, value);
    }

    public static boolean putLong(Context context, String preferencesName, String key, long value) {
        SharedPreferences settings = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /** 获取默认preferences = MyBangumi 中的long */
    public static long getLong(Context context, String key, long defaultValue) {
        return getLong(context, DEFAULT_PREFENCE_NAME, key, defaultValue);
    }

    /** 获取指定preferences 中的long */
    public static long getLong(Context context, String preferencesName, String key, long defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
        return settings.getLong(key, defaultValue);
    }

    public static boolean putFloat(Context context, String key, float value) {
        return putFloat(context, DEFAULT_PREFENCE_NAME, key, value);
    }

    public static boolean putFloat(Context context, String preferencesName, String key, float value) {
        SharedPreferences settings = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    /** 获取默认preferences = MyBangumi 中的float */
    public static float getFloat(Context context, String key, float defaultValue) {
        return getFloat(context, DEFAULT_PREFENCE_NAME, key, defaultValue);
    }

    /** 获取指定preferences 中的float */
    public static float getFloat(Context context, String preferencesName, String key, float defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
        return settings.getFloat(key, defaultValue);
    }

    public static boolean putBoolean(Context context, String key, boolean value) {
        return putBoolean(context, DEFAULT_PREFENCE_NAME, key, value);
    }

    public static boolean putBoolean(Context context, String preferencesName, String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /** 获取默认preferences = MyBangumi 中的boolean */
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return getBoolean(context, DEFAULT_PREFENCE_NAME, key, defaultValue);
    }

    /** 获取指定preferences 中的boolean */
    public static boolean getBoolean(Context context, String preferencesName, String key, boolean defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }

    /**
     *  清除默认的sharepreferences
     * @param context
     */
    public static void clear(Context context) {
        clear(context, DEFAULT_PREFENCE_NAME);
    }

    /**
     *  清除指定的sharePreferences
     * @param context
     * @param preferencesName
     */
    public static void clear(Context context, String preferencesName) {
        SharedPreferences settings = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear().commit();
    }

}
