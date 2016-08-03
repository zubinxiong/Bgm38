package me.ewriter.bangumitv.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import me.ewriter.bangumitv.api.ApiManagers;
import me.ewriter.bangumitv.api.BangumiApi;

/**
 * Created by zubin on 16/7/30.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static final BangumiApi sBangumi = ApiManagers.getBangumiSingleton();

    private final static String TAG = "BaseActivity";

    /** 获取布局的资源id，不用在调用 setContentView 方法*/
    protected abstract int getContentViewResId();

    /** 初始化views */
    protected abstract void initViews();

    protected abstract void initBeforeCreate();

    /** 根据 Activity 需要判断是否需要订阅 EventBus */
    protected abstract boolean isSubscribeEvent();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initBeforeCreate();
        super.onCreate(savedInstanceState);

        setContentView(getContentViewResId());
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isSubscribeEvent()) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        if (isSubscribeEvent()) {
            EventBus.getDefault().unregister(this);
        }
        super.onStop();
    }
}
