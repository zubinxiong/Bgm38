package me.ewriter.bangumitv.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
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
import me.ewriter.bangumitv.api.response.SearchResponse;

/**
 * Created by Zubin on 2016/8/5.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyHolder> {

    Context mContext;
    List<SearchResponse.ListBean> mList;
    onCardClickListener listener;

    public SearchAdapter(Context mContext, List<SearchResponse.ListBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_search_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        final SearchResponse.ListBean item = mList.get(position);
        if (!TextUtils.isEmpty(item.getName_cn())) {
            holder.mSearchTitle.setText(item.getName_cn());
        } else {
            holder.mSearchTitle.setText(item.getName());
        }

        String rank = "";
        if (item.getRank() != 0) {
            rank = item.getRank() + "";
        } else {
            rank = "???";
        }

        String average = "";
        if (item.getRating().getScore() != 0) {
            average = item.getRating().getScore() + "";
        } else {
            average = "???";
        }
        holder.mSearchOutline.setText(String.format(mContext.getString(R.string.score_average), average, rank));

        if (item.getImages() != null && item.getImages().getLarge() != null) {
            Picasso.with(mContext)
                    .load(item.getImages().getLarge())
                    .placeholder(R.drawable.img_on_load)
                    .config(Bitmap.Config.RGB_565)
                    .fit()
                    .centerCrop()
                    .error(R.drawable.img_on_error)
                    .into(holder.mSearchImg);
        } else {
            Picasso.with(mContext)
                    .load(R.drawable.img_on_error)
                    .placeholder(R.drawable.img_on_load)
                    .into(holder.mSearchImg);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCardClick(holder.mSearchImg, item);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface onCardClickListener {
        void onCardClick(View view, SearchResponse.ListBean listBean);
    }

    public void setOnCardListener(onCardClickListener listener) {
        this.listener = listener;
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
