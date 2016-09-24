package me.ewriter.bangumitv.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.ewriter.bangumitv.api.ApiManagers;
import me.ewriter.bangumitv.api.BangumiApi;

/**
 * Created by Zubin on 2016/9/6.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static final BangumiApi sBangumi = ApiManagers.getBangumiInstance();

    protected abstract int getContentViewResId();

    protected abstract void initView();

    protected abstract void initBefore();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBefore();
        setContentView(getContentViewResId());
        initView();
    }
}
