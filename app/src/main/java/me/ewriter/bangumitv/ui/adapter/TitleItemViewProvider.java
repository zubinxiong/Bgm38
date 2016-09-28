package me.ewriter.bangumitv.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import me.drakeet.multitype.ItemViewProvider;
import me.ewriter.bangumitv.R;

/**
 * Created by Zubin on 2016/9/27.
 */

public class TitleItemViewProvider extends ItemViewProvider<TitleItem, TitleItemViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.detail_title_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TitleItem titleItem) {
        holder.title.setText(titleItem.title);
        holder.icon.setImageResource(titleItem.titleImageRes);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);

            icon = (ImageView) itemView.findViewById(R.id.icon_image);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
