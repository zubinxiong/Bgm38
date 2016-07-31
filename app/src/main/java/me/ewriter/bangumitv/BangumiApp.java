package me.ewriter.bangumitv;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;

import org.greenrobot.greendao.database.Database;

import me.ewriter.bangumitv.dao.DaoMaster;
import me.ewriter.bangumitv.dao.DaoSession;

/**
 * Created by Zubin on 2016/7/25.
 */
public class BangumiApp extends Application {

    public static final String APP_NAME = "bangumitv";
    public static BangumiApp sAppCtx;
    public static final String DB_NAME = "bangumi.db";

    private DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        sAppCtx = this;
        LeakCanary.install(this);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, DB_NAME);
        // 不加密，加密的参考官方的demo
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    /** 返回 data/data/package/ 的路径*/
    public String getBangumiDirPath() {
        return sAppCtx.getDir(APP_NAME, Context.MODE_PRIVATE).getAbsolutePath();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

}
