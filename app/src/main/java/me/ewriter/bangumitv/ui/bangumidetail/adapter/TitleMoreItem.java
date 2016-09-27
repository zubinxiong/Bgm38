package me.ewriter.bangumitv.ui.bangumidetail.adapter;

import me.drakeet.multitype.Item;

/**
 * Created by Zubin on 2016/9/27.
 */

public class TitleMoreItem implements Item {

    public String title;
    public int imageRes;

    public TitleMoreItem(String title, int imageRes) {
        this.title = title;
        this.imageRes = imageRes;
    }
}
