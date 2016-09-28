package me.ewriter.bangumitv.ui.characters.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import me.drakeet.multitype.ItemViewProvider;
import me.ewriter.bangumitv.R;

/**
 * Created by Zubin on 2016/9/28.
 */

public class CharacterItemViewProvider extends ItemViewProvider<CharacterItem, CharacterItemViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.view_character_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull CharacterItem characterItem) {
        Picasso.with(holder.avatar.getContext())
                .load(characterItem.avatarUrl)
                .fit()
                .centerInside()
                .placeholder(R.drawable.img_on_load)
                .error(R.drawable.img_on_error)
                .into(holder.avatar);

        holder.name.setText(characterItem.name);
        holder.info.setText(characterItem.info);
        holder.cv.setText(characterItem.cvName);

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView avatar;
        TextView name;
        TextView info;
        TextView cv;

        public ViewHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.character_image);
            name = (TextView) itemView.findViewById(R.id.character_name);
            info = (TextView) itemView.findViewById(R.id.character_info);
            cv = (TextView) itemView.findViewById(R.id.cv_name);
        }
    }
}
