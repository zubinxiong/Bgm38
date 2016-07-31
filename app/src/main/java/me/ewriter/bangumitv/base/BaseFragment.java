package me.ewriter.bangumitv.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zubin on 16/7/30.
 */
public abstract class BaseFragment extends Fragment {

    /** 子类Fragment 中是否有订阅 EventBus 事件*/
    protected abstract boolean isSubscribe();

    /** 获取布局id，以便初始化*/
    protected abstract int getContentLayoutId();

    /**onViewCreated创建后的回调*/
    protected abstract void initView(boolean resued);

    private View viewCache;

    private boolean reused;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        reused = true;

        if (viewCache == null) {
            viewCache = inflater.inflate(getContentLayoutId(), container, false);
            reused = false;
        } else {
            ViewGroup viewGroup = (ViewGroup) viewCache.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(viewCache);
            }
        }

        return viewCache;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(reused);
    }

    /** 获取根视图 view */
    protected View getRootView() {
        return viewCache;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isSubscribe()) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        if (isSubscribe()) {
            EventBus.getDefault().unregister(this);
        }
        super.onStop();
    }
}
