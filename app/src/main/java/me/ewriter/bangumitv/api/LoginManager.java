package me.ewriter.bangumitv.api;

/**
 * Created by zubin on 16/7/30.
 * 使用 SharePreference 来管理用户的登录状态，auth 字段等
 */
public class LoginManager {

    private LoginManager() {

    }

    public static LoginManager getInstance() {
        LoginManager loginManager = new LoginManager();

        return loginManager;
    }

}
