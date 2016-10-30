package me.ewriter.bangumitv.api;

import android.content.Context;
import android.text.TextUtils;

import me.ewriter.bangumitv.api.response.Token;
import me.ewriter.bangumitv.constants.MyConstants;
import me.ewriter.bangumitv.utils.PreferencesUtils;

/**
 * Created by zubin on 16/7/30.
 * 使用 SharePreference 来管理用户的登录状态，auth 字段等
 */
public class LoginManager {

    public static int getUserId(Context context) {
        return PreferencesUtils.getInt(context, MyConstants.LOGIN_MANAGER_NAME,
                MyConstants.LOGIN_MANAGER_USER_ID_KEY, -1);
    }

    public static boolean setUserId(Context context, int user_id) {
        return PreferencesUtils.putInt(context, MyConstants.LOGIN_MANAGER_NAME,
                MyConstants.LOGIN_MANAGER_USER_ID_KEY, user_id);
    }

    public static String getUserHomeUrl(Context context) {
        return PreferencesUtils.getString(context, MyConstants.LOGIN_MANAGER_NAME,
                MyConstants.LOGIN_MANAGER_HOME_URL_KEY, "");
    }

    public static boolean setUserHomeUrl(Context context, String url) {
        return PreferencesUtils.putString(context, MyConstants.LOGIN_MANAGER_NAME,
                MyConstants.LOGIN_MANAGER_HOME_URL_KEY, url);
    }

    public static boolean setUserName(Context context, String userName) {
        return PreferencesUtils.putString(context, MyConstants.LOGIN_MANAGER_NAME,
                MyConstants.LOGIN_MANAGER_USER_NAME_KEY, userName);
    }

    public static String getUserName(Context context) {
        return PreferencesUtils.getString(context, MyConstants.LOGIN_MANAGER_NAME,
                MyConstants.LOGIN_MANAGER_USER_NAME_KEY, "");
    }

    public static String getUserNickName(Context context) {
        return PreferencesUtils.getString(context, MyConstants.LOGIN_MANAGER_NAME,
                MyConstants.LOGIN_MANAGER_NICKNAME_KEY, "");
    }

    public static boolean setUserNickName(Context context, String nickName) {
        return PreferencesUtils.putString(context, MyConstants.LOGIN_MANAGER_NAME,
                MyConstants.LOGIN_MANAGER_NICKNAME_KEY, nickName);
    }

    public static boolean setAuthString(Context context, String auth) {
        return PreferencesUtils.putString(context, MyConstants.LOGIN_MANAGER_NAME,
                MyConstants.LOGIN_MANAGER_AUTH_KEY, auth);
    }

    public static String getAuthString(Context context) {
        return PreferencesUtils.getString(context, MyConstants.LOGIN_MANAGER_NAME,
                MyConstants.LOGIN_MANAGER_AUTH_KEY, "");
    }

    public static boolean setAuthEncode(Context context, String auth_encode) {
        return PreferencesUtils.putString(context, MyConstants.LOGIN_MANAGER_NAME,
                MyConstants.LOGIN_MANAGER_AUTH_ENCODE_KEY, auth_encode);
    }

    public static String getAuthEncode(Context context) {
        return PreferencesUtils.getString(context, MyConstants.LOGIN_MANAGER_NAME,
                MyConstants.LOGIN_MANAGER_AUTH_ENCODE_KEY, "");
    }


    public static String getLargeAvatar(Context context) {
        return PreferencesUtils.getString(context, MyConstants.LOGIN_MANAGER_NAME,
                MyConstants.LOGIN_MANAGER_AVATAR_KEY, "");
    }

    public static boolean setLargeAvatar(Context context, String large_avatar_url) {
        return PreferencesUtils.putString(context, MyConstants.LOGIN_MANAGER_NAME,
                MyConstants.LOGIN_MANAGER_AVATAR_KEY, large_avatar_url);
    }

    public static String getSign(Context context) {
        return PreferencesUtils.getString(context, MyConstants.LOGIN_MANAGER_NAME,
                MyConstants.LOGIN_MANAGER_SIGN_KEY, "");
    }

    public static boolean setSign(Context context, String sign) {
        return PreferencesUtils.putString(context, MyConstants.LOGIN_MANAGER_NAME,
                MyConstants.LOGIN_MANAGER_SIGN_KEY, sign);
    }

    public static void saveToken(Context context, Token token) {
        setUserId(context, token.getId());
        setUserHomeUrl(context, token.getUrl());
        setUserName(context, token.getUsername());
        setUserNickName(context, token.getNickname());
        if (token.getAvatar()!= null && token.getAvatar().getLarge() != null) {
            setLargeAvatar(context, token.getAvatar().getLarge());
        }
        setAuthString(context, token.getAuth());
        setAuthEncode(context, token.getAuthEncode());
        setSign(context, token.getSign());
    }

    public static boolean isLogin(Context context) {
        if (!TextUtils.isEmpty(getAuthString(context))) {
            return true;
        } else {
            return false;
        }
    }

    public static void logout(Context context) {
        PreferencesUtils.clear(context, MyConstants.LOGIN_MANAGER_NAME);
    }

}
