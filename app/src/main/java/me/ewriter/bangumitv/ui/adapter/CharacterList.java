package me.ewriter.bangumitv.ui.adapter;

import java.util.List;

import me.drakeet.multitype.Item;
import me.ewriter.bangumitv.api.entity.AnimeCharacterEntity;

/**
 * Created by Zubin on 2016/9/27.
 */

public class CharacterList implements Item {

    List<AnimeCharacterEntity> mList;

    public CharacterList(List<AnimeCharacterEntity> mList) {
        this.mList = mList;
    }
}
