package me.ewriter.bangumitv.ui.commonAdapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import me.drakeet.multitype.ItemViewProvider;
import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.constants.MyConstants;
import me.ewriter.bangumitv.ui.characters.CharacterActivity;
import me.ewriter.bangumitv.ui.persons.PersonsActivity;
import me.ewriter.bangumitv.ui.progress.ProgressActivity;

/**
 * Created by Zubin on 2016/9/27.
 */

public class TitleMoreViewProvider extends ItemViewProvider<TitleMoreItem, TitleMoreViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.detail_title_more_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final TitleMoreItem titleMoreItem) {
        holder.title.setText(titleMoreItem.title);
        holder.icon.setImageResource(titleMoreItem.imageRes);

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String des = titleMoreItem.destination;

                Intent intent = null;
                if (des.equals(MyConstants.DES_EP)) {
                    intent = new Intent(holder.item.getContext(), ProgressActivity.class);
                } else if (des.equals(MyConstants.DES_CHARACTER)) {
                    intent = new Intent(holder.item.getContext(), CharacterActivity.class);
                    intent.putExtra("subjectId", titleMoreItem.subjectId);
                } else if (des.equals(MyConstants.DES_PERSON)) {
                    intent = new Intent(holder.item.getContext(), PersonsActivity.class);
                    if (!TextUtils.isEmpty(titleMoreItem.extra)) {
                        intent.putExtra("extra", titleMoreItem.extra);
                        intent.putExtra("subjectId", titleMoreItem.subjectId);
                    }
                }
                holder.item.getContext().startActivity(intent);
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView icon;
        View item;

        public ViewHolder(View itemView) {
            super(itemView);
            item = itemView;
            title = (TextView) itemView.findViewById(R.id.title);
            icon = (ImageView) itemView.findViewById(R.id.icon_image);
        }
    }
}
