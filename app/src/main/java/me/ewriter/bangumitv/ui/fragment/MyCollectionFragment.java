package me.ewriter.bangumitv.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.base.BaseFragment;
import me.ewriter.bangumitv.utils.LogUtil;

/**
 * Created by zubin on 16/7/30.
 */
public class MyCollectionFragment extends BaseFragment {

    @Override
    protected boolean isSubscribe() {
        return false;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_my_collection;
    }

    @Override
    protected void initView(boolean resued) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(LogUtil.ZUBIN, "MyCollectionFragment onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.d(LogUtil.ZUBIN, "MyCollectionFragment onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtil.d(LogUtil.ZUBIN, "MyCollectionFragment onAttach");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.d(LogUtil.ZUBIN, "MyCollectionFragment onActivityCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.d(LogUtil.ZUBIN, "MyCollectionFragment onStart");
    }


    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d(LogUtil.ZUBIN, "MyCollectionFragment onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d(LogUtil.ZUBIN, "MyCollectionFragment onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.d(LogUtil.ZUBIN, "MyCollectionFragment onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.d(LogUtil.ZUBIN, "MyCollectionFragment onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d(LogUtil.ZUBIN, "MyCollectionFragment onDestroy");
    }
}
