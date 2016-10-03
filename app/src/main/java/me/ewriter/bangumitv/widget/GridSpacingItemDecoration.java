package me.ewriter.bangumitv.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import me.ewriter.bangumitv.ui.progress.adapter.MyEpAdapter;
import me.ewriter.bangumitv.utils.LogUtil;

/**
 * Created by Zubin on 2016/8/4.
 */
public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int spacing;
    private boolean includeEdge;

    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // outRect 负责存储 item 之间的间距
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = spacing;
            }
            outRect.bottom = spacing; // item bottom
        } else {
            outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing; // item top
            }
        }

//        int position = parent.getChildAdapterPosition(view); // item position
//        int column = position % spanCount; // item column
//        int type = parent.getAdapter().getItemViewType(position);
//
//        if (type == MyEpAdapter.TYPE_TITLE) {
//            LogUtil.d(LogUtil.ZUBIN, "title position = " + position);
//            outRect.left = spacing;
//            outRect.right = spacing;
//        } else if (type == MyEpAdapter.TYPE_GRID) {
//            LogUtil.d(LogUtil.ZUBIN, "grid position = " + position);
//
//            // 普通的只有一个 标题的情况, 余数为1 是第一个，余数为0 为最后一个
//            if (column == 1) {
//                outRect.left = spacing;
//                outRect.right = spacing / 2;
//            } else if (column == 6) {
//                outRect.left = spacing / 2;
//                outRect.right = spacing;
//            } else {
//                outRect.left = spacing / 2;
//                outRect.right = spacing / 2;
//            }
//        }
    }
}
