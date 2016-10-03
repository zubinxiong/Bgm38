package me.ewriter.bangumitv.ui.home;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import me.ewriter.bangumitv.base.BasePresenter;
import me.ewriter.bangumitv.base.BaseView;

/**
 * Created by Zubin on 2016/9/18.
 */
public interface HomeContract {

    interface Presenter extends BasePresenter {

        /**更新当前的tag*/
        void updateCurrentTag(String tag);

        /**获取当前选中的tag*/
        String getCurrentTag();

        /**替换选中fragment*/
        void updateSelectedFragment(FragmentManager manager, String tag);

        /**打开搜索*/
        void openSearch(Activity context);

        /**打开超展开*/
        void openExpand(Activity context);

        /**退出登录*/
        void logout();

        /**打开关于*/
        void openAbout(Activity activity);

        /**登录*/
        void login(Activity activity);

        /** 切换主题 */
        void updateTheme(boolean isChecked, Activity activity);

        /** 检查是否已登录 */
        void checkLogin();

    }

    interface View extends BaseView<Presenter> {
        /**根据id设置抽屉选中状态*/
        void updateDrawChecked(int id);

        /**关闭drawer*/
        void closeDrawer();

        /**打开drawer*/
        void openDrawer();

        /**显示退出登录的dialog*/
        void showLogoutDialog();

        /*更新夜间模式的切换按钮状态 **/
        void updateSwitcher();

        /** 设置登录后的用户头像及名称 */
        void setUserInfo();
    }
}
