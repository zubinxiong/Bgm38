package me.ewriter.bangumitv.ui.bangumidetail;

import me.drakeet.multitype.Item;

/**
 * Created by Zubin on 2016/9/26.
 * 用于显示网页上左侧的信息
 */

public class TextItem implements Item {
    public String text;

    public TextItem(String text) {
        this.text = text;
    }
}
