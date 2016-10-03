package me.ewriter.bangumitv.ui.characters.adapter;

import me.drakeet.multitype.Item;

/**
 * Created by Zubin on 2016/9/28.
 */

public class CharacterItem implements Item {

    /** 先日文再中文*/
    public String name;
    public String avatarUrl;
    public String info;
    public String cvName;

    public CharacterItem(String name, String avatarUrl, String info, String cvName) {
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.info = info;
        this.cvName = cvName;
    }
}
