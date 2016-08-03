package me.ewriter.bangumitv.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.ewriter.bangumitv.R;

/**
 * Created by Zubin on 2016/8/1.
 */
public class BangumiDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    List<String> mList;
    Context mContext;

    /** 标题,或者换行，当内容为空时换行 */
    public static final int TYPE_TITLE = 1;
    /** 圆形头像信息，例如角色介绍等 */
    public static final int TYPE_CARD = 2;
    /** 每集的grid */
    public static final int TYPE_GRID = 3;

    public BangumiDetailAdapter(Context context, List list) {
        mContext = context;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_TITLE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_detail_adapter_title, parent, false);
            return new TitleHolder(view);
        } else if (viewType == TYPE_CARD) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_detail_adapter_card, parent, false);
            return new CardHolder(view);
        } else if (viewType == TYPE_GRID) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_detail_adapter_grid, parent, false);
            return new GridHolder(view);
        }

        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == 7) {
            return TYPE_TITLE;
        } else if (position == 8 || position == 9){
            return TYPE_CARD;
        } else {
            return TYPE_GRID;
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TitleHolder) {
            TitleHolder titleHolder = (TitleHolder) holder;
            titleHolder.title.setText("Title " + position);
        } else if (holder instanceof GridHolder) {
            GridHolder gridHolder = (GridHolder) holder;
            gridHolder.title.setText("Grid" + position);
        } else if (holder instanceof CardHolder) {
            CardHolder cardHolder = (CardHolder) holder;
            cardHolder.title.setText("Card" + position);
            Picasso.with(mContext)
                    .load(R.drawable.role_img_test_medium)
                    .config(Bitmap.Config.RGB_565)
                    .resize(200, 200)
                    .centerInside()
                    .into(cardHolder.image);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class TitleHolder extends RecyclerView.ViewHolder {

        TextView title;

        public TitleHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView;
        }
    }

    class GridHolder extends RecyclerView.ViewHolder {

        TextView title;

        public GridHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView;
        }
    }

    class CardHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;

        public CardHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.role_img);
            title = (TextView) itemView.findViewById(R.id.role_name);
        }
    }
}
