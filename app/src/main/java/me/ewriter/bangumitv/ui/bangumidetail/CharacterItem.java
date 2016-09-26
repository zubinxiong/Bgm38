package me.ewriter.bangumitv.ui.bangumidetail;

import me.drakeet.multitype.Item;

/**
 * Created by Zubin on 2016/9/26.
 */

public class CharacterItem implements Item {

    public String imageUrl;
    public String nameCn;
    public String nameJp;
    public String roleType;
    public String cvName;

    public CharacterItem(String imageUrl, String nameCn, String roleType) {
        this.imageUrl = imageUrl;
        this.nameCn = nameCn;
        this.roleType = roleType;
    }
}
