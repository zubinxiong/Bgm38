package me.ewriter.bangumitv.ui.progress.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.ewriter.bangumitv.BangumiApp;
import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.api.entity.AnimeEpEntity;

/**
 * Created by Zubin on 2016/9/29.
 */

public class MyEpAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static final int TYPE_TITLE = 0;
    public static final int TYPE_GRID = 1;

    private onItemClickListener listener;
    List<AnimeEpEntity> mList;
    Context mContext;

    public MyEpAdapter(List<AnimeEpEntity> mList, Context context) {
        this.mList = mList;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_GRID) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_ep_item, parent, false);
            return new GridHolder(view);
        } else if (viewType == TYPE_TITLE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_title_item, parent, false);
            return new TitleHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final AnimeEpEntity entity = mList.get(position);

        if (holder instanceof GridHolder) {
            GridHolder gridHolder = (GridHolder) holder;
            gridHolder.textView.setText(entity.getDisplayName());

            updateStatus(entity, gridHolder.textView);

            gridHolder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v, position, entity);
                }
            });
        } else if (holder instanceof TitleHolder) {
            TitleHolder titleHolder = (TitleHolder) holder;
            titleHolder.title.setText(entity.getTitle());
            Picasso.with(titleHolder.title.getContext())
                    .load(entity.getIconRes())
                    .into(titleHolder.icon);

        }
    }

    /** 更新 grid 显示 */
    private void updateStatus(AnimeEpEntity entity, TextView textView) {
        String epStatus = entity.getEpStatusName();

        if (epStatus.equals("Queue")) {
            // 想看
            textView.setTextColor(BangumiApp.sAppCtx.getResources().getColor(R.color.white));
            textView.setBackground(BangumiApp.sAppCtx.getResources().getDrawable(R.drawable.shape_square_queue));
            textView.getPaint().setFlags(0);
        } else if (epStatus.equals("Drop")) {
            // 弃番
            textView.setTextColor(BangumiApp.sAppCtx.getResources().getColor(R.color.white));
            textView.setBackground(BangumiApp.sAppCtx.getResources().getDrawable(R.drawable.shape_square_watched));
            textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        } else if (epStatus.equals("Watched")) {
            // 看过
            textView.setTextColor(BangumiApp.sAppCtx.getResources().getColor(R.color.white));
            textView.setBackground(BangumiApp.sAppCtx.getResources().getDrawable(R.drawable.shape_square_watched));
            textView.getPaint().setFlags(0);
        } else if (epStatus.equals("NA")) {
            // 未放送
            textView.setTextColor(BangumiApp.sAppCtx.getResources().getColor(R.color.white));
            textView.setBackground(BangumiApp.sAppCtx.getResources().getDrawable(R.drawable.shape_square_disable));
            textView.getPaint().setFlags(0);
        } else {
            // 默认状态
            textView.setTextColor(mContext.getResources().getColor(R.color.calendar_title_color));
            textView.setBackground(mContext.getResources().getDrawable(R.drawable.shape_square_normal));
            textView.getPaint().setFlags(0);
        }
    }

    public interface onItemClickListener {
        void onItemClick(View view, int position, AnimeEpEntity entity);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class TitleHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView title;

        public TitleHolder(View itemView) {
            super(itemView);

            icon = (ImageView) itemView.findViewById(R.id.icon_image);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

    static class GridHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public GridHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }
}
