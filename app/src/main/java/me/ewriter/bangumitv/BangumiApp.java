package me.ewriter.bangumitv;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;

import org.greenrobot.greendao.database.Database;

import me.ewriter.bangumitv.constants.MyConstants;
import me.ewriter.bangumitv.dao.DaoMaster;
import me.ewriter.bangumitv.dao.DaoSession;
import me.ewriter.bangumitv.utils.PreferencesUtils;

/**
 * Created by Zubin on 2016/7/25.
 */
public class BangumiApp extends Application {

    public static BangumiApp sAppCtx;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        sAppCtx = this;

//        CrashReport.initCrashReport(getApplicationContext(), MyConstants.BUGLY_APPID, true);
        LeakCanary.install(this);

        initTheme();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, MyConstants.DB_NAME, null);
        // 不加密，加密的参考官方的demo
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    @SuppressWarnings("WrongConstant")
    private void initTheme() {
        boolean isNight = PreferencesUtils.getBoolean(BangumiApp.sAppCtx, MyConstants.THEME_KEY, false);
        if (isNight) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    /** 返回 data/data/package/ 的路径*/
    public String getBangumiDirPath() {
        return sAppCtx.getDir(MyConstants.APP_NAME, Context.MODE_PRIVATE).getAbsolutePath();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
