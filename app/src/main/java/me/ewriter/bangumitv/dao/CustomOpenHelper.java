package me.ewriter.bangumitv.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import me.ewriter.bangumitv.utils.LogUtil;

/**
 * Created by Zubin on 2016/8/1.
 */
public class CustomOpenHelper extends DaoMaster.OpenHelper {

    public CustomOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtil.d(LogUtil.ZUBIN, "update schema from " + oldVersion + " to " + newVersion);
        super.onUpgrade(db, oldVersion, newVersion);
    }


}
