package me.ewriter.bangumitv.ui.bangumidetail.adapter;

import java.util.List;

import me.drakeet.multitype.Item;
import me.ewriter.bangumitv.api.entity.AnimeEpEntity;

/**
 * Created by Zubin on 2016/9/27.
 */

public class DetailEpList implements Item {

    List<AnimeEpEntity> mList;

    public DetailEpList(List<AnimeEpEntity> mList) {
        this.mList = mList;
    }
}
