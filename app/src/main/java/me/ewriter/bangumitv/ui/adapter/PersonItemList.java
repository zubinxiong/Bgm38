package me.ewriter.bangumitv.ui.adapter;

import java.util.List;

import me.drakeet.multitype.Item;

/**
 * Created by Zubin on 2016/9/28.
 */

public class PersonItemList implements Item{

    public List<PersonItem> mList;

    public PersonItemList(List<PersonItem> mList) {
        this.mList = mList;
    }
}
