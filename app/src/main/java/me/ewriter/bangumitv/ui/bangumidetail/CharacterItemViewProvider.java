package me.ewriter.bangumitv.ui.bangumidetail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import me.drakeet.multitype.ItemViewProvider;
import me.drakeet.multitype.MultiTypeAdapter;
import me.ewriter.bangumitv.R;

/**
 * Created by Zubin on 2016/9/26.
 */

public class CharacterItemViewProvider extends ItemViewProvider<CharacterItem, CharacterItemViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.detail_character_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull CharacterItem characterItem) {
        Picasso.with(holder.mNameCn.getContext())
                .load(characterItem.imageUrl)
                .placeholder(R.drawable.img_on_load)
                .error(R.drawable.img_on_error)
                .into(holder.mAvatar);

        holder.mNameCn.setText(characterItem.nameCn);
        holder.mRoleType.setText(characterItem.roleType);
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
