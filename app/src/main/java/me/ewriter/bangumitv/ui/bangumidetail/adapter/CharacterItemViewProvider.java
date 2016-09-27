package me.ewriter.bangumitv.ui.bangumidetail.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.drakeet.multitype.ItemViewProvider;
import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.api.entity.AnimeCharacterEntity;

/**
 * Created by Zubin on 2016/9/27.
 */

public class CharacterItemViewProvider extends ItemViewProvider<CharacterList, CharacterItemViewProvider.ViewHolder> {


    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_character_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull CharacterList characterList) {
        holder.setList(characterList.mList);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView recyclerView;
        private CharacterAdapter adapter;

        public ViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerview);
            GridLayoutManager layoutManager = new GridLayoutManager(recyclerView.getContext(), 2);
            adapter = new CharacterAdapter();
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }

        private void setList(List<AnimeCharacterEntity> list) {
            adapter.setList(list);
            adapter.notifyDataSetChanged();
        }
    }
}
