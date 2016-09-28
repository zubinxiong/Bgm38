package me.ewriter.bangumitv.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.api.entity.AnimeCharacterEntity;

/**
 * Created by Zubin on 2016/9/27.
 */

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {

    List<AnimeCharacterEntity> mList;

    public void setList(List<AnimeCharacterEntity> list) {
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_character_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AnimeCharacterEntity characterItem = mList.get(position);

        Picasso.with(holder.mNameCn.getContext())
                .load(characterItem.getRoleLargeImageUrl())
                .placeholder(R.drawable.img_on_load)
                .error(R.drawable.img_on_error)
                .into(holder.mAvatar);

        if (!TextUtils.isEmpty(characterItem.getRoleNameCn())) {
            holder.mNameCn.setText(characterItem.getRoleNameCn());
        } else {
            holder.mNameCn.setText(characterItem.getRoleNameJp());
        }

        holder.mRoleType.setText(characterItem.getRoleType());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mAvatar;
        TextView mNameCn, mNameJp, mCvName, mRoleType;

        public ViewHolder(View itemView) {
            super(itemView);
            mAvatar = (ImageView) itemView.findViewById(R.id.character_image);
            mNameCn = (TextView) itemView.findViewById(R.id.character_name_cn);
            mRoleType = (TextView) itemView.findViewById(R.id.character_type);
        }
    }
}
