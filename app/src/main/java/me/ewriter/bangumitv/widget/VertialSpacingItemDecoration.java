package me.ewriter.bangumitv.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Zubin on 2016/8/9.
 */
public class VertialSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public VertialSpacingItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = space;
        outRect.bottom = space;
    }
}
