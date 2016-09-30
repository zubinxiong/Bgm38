package me.ewriter.bangumitv.ui.characters.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import me.drakeet.multitype.ItemViewProvider;
import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.ui.picture.PictureViewActivity;

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
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final CharacterItem characterItem) {
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

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.name.getContext(), PictureViewActivity.class);
                intent.putExtra(PictureViewActivity.EXTRA_IMAGE_URL, characterItem.avatarUrl);
                intent.putExtra(PictureViewActivity.EXTRA_IMAGE_TEXT, characterItem.name + "\n" + characterItem.cvName);
                v.getContext().startActivity(intent,
                        ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) v.getContext(), holder.avatar, "img").toBundle());
            }
        });

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView avatar;
        TextView name;
        TextView info;
        TextView cv;
        View item;

        public ViewHolder(View itemView) {
            super(itemView);
            item = itemView;
            avatar = (ImageView) itemView.findViewById(R.id.character_image);
            name = (TextView) itemView.findViewById(R.id.character_name);
            info = (TextView) itemView.findViewById(R.id.character_info);
            cv = (TextView) itemView.findViewById(R.id.cv_name);
        }
    }
}
