package me.ewriter.bangumitv.ui.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.api.entity.SearchItemEntity;

/**
 * Created by Zubin on 2016/9/22.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyHolder> {

    List<SearchItemEntity> mList;
    Context mContext;
    onItemClickListener listener;

    public SearchAdapter(List<SearchItemEntity> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_search_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        final SearchItemEntity item = mList.get(position);
        holder.mSearchTitle.setText(item.getNormalName());
        holder.mSearchOutline.setText(item.getInfo());

        Picasso.with(mContext)
                .load(item.getCoverImageUrl())
                .placeholder(R.drawable.img_on_load)
                .fit()
                .centerCrop()
                .error(R.drawable.img_on_error)
                .into(holder.mSearchImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.mSearchImg, item);
            }
        });
    }

    public interface onItemClickListener {
        void onItemClick(View view, SearchItemEntity entity);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        View itemView;
        ImageView mSearchImg;
        TextView mSearchTitle;
        TextView mSearchOutline;

        public MyHolder(View itemView) {
            super(itemView);

            this.itemView = itemView;
            mSearchImg = (ImageView) itemView.findViewById(R.id.search_img);
            mSearchTitle = (TextView) itemView.findViewById(R.id.search_title);
            mSearchOutline = (TextView) itemView.findViewById(R.id.search_outline);
        }
    }
}
