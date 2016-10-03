package me.ewriter.bangumitv.widget.headerfooter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import me.ewriter.bangumitv.utils.LogUtil;

/**
 * Created by cundong on 2015/10/9.
 * Created by Zubin on 2016/9/21.
 */

public class HeaderAndFooterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_HEADER_VIEW = Integer.MIN_VALUE;
    private static final int TYPE_FOOTER_VIEW = Integer.MIN_VALUE + 1;
    private static final String TAG = HeaderAndFooterAdapter.class.getName();

    private RecyclerView.Adapter<RecyclerView.ViewHolder> mWrapperAdapter;

    // 存储header 和footer
    private ArrayList<View> mHeaderViews = new ArrayList<>();
    private ArrayList<View> mFooterViews = new ArrayList<>();

    /** 自己监听数据的变化，关键部分就是这里对数据的处理*/
    private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            LogUtil.d(TAG, "onChange");
            // 外部其实是被包括的data调用才会调用到这个方法，否则不会走到这里面，
            // 但是还要通知到heder footer 更新，所以本身这里自己也需要调用notifydatasetchanged，同理下面其他几个方法也需要自己在调用
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            LogUtil.d(TAG, "onItemRangeChanged");
            notifyItemRangeChanged(getHeaderViewsCount() + positionStart, itemCount);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            LogUtil.d(TAG, "onItemRangeInserted");
            notifyItemRangeInserted(getHeaderViewsCount() + positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            LogUtil.d(TAG, "onItemRangeRemoved");
            notifyItemRangeRemoved(getHeaderViewsCount() + positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            LogUtil.d(TAG, "onItemRangeMoved");
            notifyItemRangeChanged(fromPosition + getHeaderViewsCount(), toPosition + getHeaderViewsCount() + itemCount);
        }
    };


    public HeaderAndFooterAdapter(RecyclerView.Adapter dataAdapter) {
        setAdapter(dataAdapter);
    }

    private void setAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        if (mWrapperAdapter != null) {
            mWrapperAdapter.unregisterAdapterDataObserver(mDataObserver);
        }

        mWrapperAdapter = adapter;
        mWrapperAdapter.registerAdapterDataObserver(mDataObserver);
    }

    public RecyclerView.Adapter getInnerAdapter() {
        return mWrapperAdapter;
    }

    public void addHeaderView(View header) {

        if (header == null) {
            throw new RuntimeException("header is null");
        }

        mHeaderViews.add(header);
        this.notifyDataSetChanged();
    }

    public void addFooterView(View footer) {

        if (footer == null) {
            throw new RuntimeException("footer is null");
        }

        mFooterViews.add(footer);
        this.notifyDataSetChanged();
    }

    public void removeHeaderView(View view) {
        mHeaderViews.remove(view);
        this.notifyDataSetChanged();
    }

    public void removeFooterView(View view) {
        mFooterViews.remove(view);
        this.notifyDataSetChanged();
    }

    public int getHeaderViewsCount() {
        return mHeaderViews.size();
    }

    public int getFooterViewsCount() {
        return mFooterViews.size();
    }

    /** 返回第一个FooterView */
    public View getFooterView() {
        return  getFooterViewsCount()>0 ? mFooterViews.get(0) : null;
    }

    /** 返回第一个HeaderView */
    public View getHeaderView() {
        return  getHeaderViewsCount()>0 ? mHeaderViews.get(0) : null;
    }

    public boolean isHeader(int postion) {
        return getHeaderViewsCount() > 0 && postion == 0;
    }

    public boolean isFooter(int position) {
        int lastPosition = getItemCount() - 1;
        return getFooterViewsCount() > 0 && position == lastPosition;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int headerViewsCount = getHeaderViewsCount();
        if (viewType < TYPE_HEADER_VIEW + headerViewsCount) {
            return new ViewHolder(mHeaderViews.get(viewType - TYPE_HEADER_VIEW));
        } else if (viewType >= TYPE_FOOTER_VIEW && viewType < Integer.MAX_VALUE / 2) {
            return new ViewHolder(mFooterViews.get(viewType - TYPE_FOOTER_VIEW));
        } else {
            return mWrapperAdapter.onCreateViewHolder(parent, viewType - Integer.MAX_VALUE / 2);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int headerViewsCountCount = getHeaderViewsCount();
        if (position >= headerViewsCountCount && position < headerViewsCountCount + mWrapperAdapter.getItemCount()) {
            mWrapperAdapter.onBindViewHolder(holder, position - headerViewsCountCount);
        } else {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if(layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
            }
        }
    }

    @Override
    public int getItemCount() {
        return getHeaderViewsCount() + getFooterViewsCount() + mWrapperAdapter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        // 数据的总个数，不包括header 和 footer
        int dataCount = mWrapperAdapter.getItemCount();
        int headerViewsCountCount = getHeaderViewsCount();

        if (position < headerViewsCountCount) {
            return TYPE_HEADER_VIEW + position;
        } else if (headerViewsCountCount <= position && position < headerViewsCountCount + dataCount) {
            int dataItemViewType = mWrapperAdapter.getItemViewType(position - headerViewsCountCount);

            if (dataItemViewType >= Integer.MAX_VALUE / 2) {
                throw new IllegalArgumentException("your adapter's return value of getViewTypeCount() must < Integer.MAX_VALUE / 2");
            }

            return dataItemViewType + Integer.MAX_VALUE / 2;
        } else {
            return TYPE_FOOTER_VIEW + position - headerViewsCountCount - dataCount;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
