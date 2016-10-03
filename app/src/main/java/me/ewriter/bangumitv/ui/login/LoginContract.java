package me.ewriter.bangumitv.ui.login;

import android.app.Activity;

import me.ewriter.bangumitv.base.BasePresenter;
import me.ewriter.bangumitv.base.BaseView;

/**
 * Created by Zubin on 2016/9/19.
 */
public interface LoginContract {

    interface Presenter extends BasePresenter {
        /** 点击注册提醒跳转 */
        void openLoginHint(Activity activity);

        /** 登录 */
        void login(String email, String password);
    }

    interface View extends BaseView<Presenter> {
        /** 显示登录中的dialog */
        void showLoginProDialog();

        /** 隐藏登录中的dialog */
        void  hideLoginProDialog();

        /** 邮箱错误提示 */
        void showEmailError();

        /** 密码错误提示 */
        void showPassError();

        /** 关闭当前 activity */
        void closeActivity();
    }
}
