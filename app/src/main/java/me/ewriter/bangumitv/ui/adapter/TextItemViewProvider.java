package me.ewriter.bangumitv.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.drakeet.multitype.ItemViewProvider;
import me.ewriter.bangumitv.R;

/**
 * Created by Zubin on 2016/9/26.
 */

public class TextItemViewProvider extends ItemViewProvider<TextItem, TextItemViewProvider.TextHolder> {

    @NonNull
    @Override
    protected TextHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.detail_text_item, parent, false);
        return new TextHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull TextHolder holder, @NonNull final TextItem textItem) {
        holder.textView.setText(textItem.text);
    }

    static class TextHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public TextHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }
}
