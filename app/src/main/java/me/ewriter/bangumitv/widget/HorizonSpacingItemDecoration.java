package me.ewriter.bangumitv.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Zubin on 2016/9/27.
 */

public class HorizonSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public HorizonSpacingItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
    }
}
