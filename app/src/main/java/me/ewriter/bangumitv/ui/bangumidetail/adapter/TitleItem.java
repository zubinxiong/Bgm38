package me.ewriter.bangumitv.ui.bangumidetail.adapter;

import me.drakeet.multitype.Item;
import me.ewriter.bangumitv.R;

/**
 * Created by Zubin on 2016/9/27.
 */

public class TitleItem implements Item {

    public String title;
    public int titleImageRes;

    public TitleItem(String title, int titleImageRes) {
        this.title = title;
        this.titleImageRes = titleImageRes;
    }
}
