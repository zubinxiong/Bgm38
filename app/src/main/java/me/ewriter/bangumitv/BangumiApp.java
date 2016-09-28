package me.ewriter.bangumitv;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.squareup.leakcanary.LeakCanary;

import org.greenrobot.greendao.database.Database;

import me.drakeet.multitype.MultiTypePool;
import me.ewriter.bangumitv.constants.MyConstants;
import me.ewriter.bangumitv.dao.DaoMaster;
import me.ewriter.bangumitv.dao.DaoSession;
import me.ewriter.bangumitv.ui.bangumidetail.adapter.DetailCharacterItemViewProvider;
import me.ewriter.bangumitv.ui.bangumidetail.adapter.DetailCharacterList;
import me.ewriter.bangumitv.ui.bangumidetail.adapter.DetailEpItemViewProvider;
import me.ewriter.bangumitv.ui.bangumidetail.adapter.DetailEpList;
import me.ewriter.bangumitv.ui.characters.adapter.CharacterItem;
import me.ewriter.bangumitv.ui.characters.adapter.CharacterItemViewProvider;
import me.ewriter.bangumitv.ui.persons.adapter.PersonItemList;
import me.ewriter.bangumitv.ui.persons.adapter.PersonItemViewProvider;
import me.ewriter.bangumitv.ui.commonAdapter.TextItem;
import me.ewriter.bangumitv.ui.commonAdapter.TextItemViewProvider;
import me.ewriter.bangumitv.ui.commonAdapter.TitleItem;
import me.ewriter.bangumitv.ui.commonAdapter.TitleItemViewProvider;
import me.ewriter.bangumitv.ui.commonAdapter.TitleMoreItem;
import me.ewriter.bangumitv.ui.commonAdapter.TitleMoreViewProvider;
import me.ewriter.bangumitv.ui.progress.adapter.EpItemViewProvider;
import me.ewriter.bangumitv.ui.progress.adapter.EpList;
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


        initTheme();

        registerMutiType();

//        CrashReport.initCrashReport(getApplicationContext(), MyConstants.BUGLY_APPID, true);
        LeakCanary.install(this);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, MyConstants.DB_NAME, null);
        // 不加密，加密的参考官方的demo
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    private void registerMutiType() {
        MultiTypePool.register(TextItem.class, new TextItemViewProvider());
        MultiTypePool.register(TitleItem.class, new TitleItemViewProvider());
        MultiTypePool.register(TitleMoreItem.class, new TitleMoreViewProvider());

        MultiTypePool.register(DetailCharacterList.class, new DetailCharacterItemViewProvider());
        MultiTypePool.register(DetailEpList.class, new DetailEpItemViewProvider());

        MultiTypePool.register(PersonItemList.class, new PersonItemViewProvider());
        MultiTypePool.register(CharacterItem.class, new CharacterItemViewProvider());
        MultiTypePool.register(EpList.class, new EpItemViewProvider());
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
