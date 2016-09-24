package me.ewriter.bangumitv.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.ewriter.bangumitv.utils.LogUtil;

/**
 * Created by Zubin on 2016/9/6.
 */
public abstract class BaseFragment extends Fragment {
    /** 获取布局id，以便初始化*/
    protected abstract int getContentLayoutId();

    /**onViewCreated创建后的回调*/
    protected abstract void initView(boolean reused);

    private View viewCache;

    private boolean reused;

    /**控件是否初始化完成*/
    private boolean isViewCreated;
    /**数据是否已加载完毕*/
    private boolean isLoadDataCompleted;
    private boolean isVisibleToUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        reused = true;

        if (viewCache == null) {
            viewCache = inflater.inflate(getContentLayoutId(), null);
            reused = false;
        } else {
            ViewGroup viewGroup = (ViewGroup) viewCache.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(viewCache);
            }
        }
        isViewCreated = true;
        return viewCache;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(reused);
        LogUtil.d(LogUtil.ZUBIN, "onActivityCreated");
//        if (getUserVisibleHint()) {
//            isLoadDataCompleted = true;
//            loadData();
//        }
        prepareFetchData();
    }

    public boolean prepareFetchData() {
        return prepareFetchData(true);
    }

    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewCreated && (!isLoadDataCompleted || forceUpdate)) {
            loadData();
            isLoadDataCompleted = true;
            return true;
        }
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    /** 获取根视图 view */
    protected View getRootView() {
        return viewCache;
    }

    // 实现懒加载, 在 onCreateView 前运行
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
//        if (isVisibleToUser && isViewCreated && !isLoadDataCompleted) {
//            isLoadDataCompleted = true;
//            loadData();
//        }
    }

    /**子类用于加载数据*/
    protected abstract void loadData();
}
