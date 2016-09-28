package me.ewriter.bangumitv.ui.adapter;

import java.util.List;

import me.drakeet.multitype.Item;
import me.ewriter.bangumitv.api.entity.AnimeEpEntity;

/**
 * Created by Zubin on 2016/9/27.
 */

public class EpList implements Item {

    List<AnimeEpEntity> mList;

    public EpList(List<AnimeEpEntity> mList) {
        this.mList = mList;
    }
}
