package me.ewriter.bangumitv.ui.commonAdapter;

import me.drakeet.multitype.Item;

/**
 * Created by Zubin on 2016/9/27.
 */

public class TitleMoreItem implements Item {

    public String title;
    public int imageRes;
    public String subjectId;
    public String destination;

    public String extra;


    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public TitleMoreItem(String title, int imageRes, String subjectId, String destination) {
        this.title = title;
        this.imageRes = imageRes;
        this.subjectId = subjectId;
        this.destination = destination;
    }
}
