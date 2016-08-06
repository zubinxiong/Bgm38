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
import me.ewriter.bangumitv.api.entity.BangumiDetailEntity;

/**
 * Created by Zubin on 2016/8/1.
 */
public class BangumiDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    List<BangumiDetailEntity> mList;
    Context mContext;

    /** 标题,或者换行，当内容为空时换行 */
    public static final int TYPE_TITLE = 1;
    /** 圆形头像信息，例如角色介绍等 */
    public static final int TYPE_CARD = 2;
    /** 每集的grid */
    public static final int TYPE_GRID = 3;
    /** 标题下的纯文字,和 TYPE_TITLE 只是文字颜色大小有些不同*/
    public static final int TYPE_CONTENT = 4;

    public BangumiDetailAdapter(Context context, List list) {
        mContext = context;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_TITLE || viewType == TYPE_CONTENT) {
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
        return  mList.get(position).getType();
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BangumiDetailEntity entity = mList.get(position);
        if (holder instanceof TitleHolder) {
            TitleHolder titleHolder = (TitleHolder) holder;
            if (entity.getType() == TYPE_CONTENT) {
                titleHolder.title.setTextColor(mContext.getResources().getColor(R.color.md_grey_500));
            } else if (entity.getType() == TYPE_TITLE) {
                titleHolder.title.setTextColor(mContext.getResources().getColor(R.color.calendar_title_color));
            }
            titleHolder.title.setText(entity.getTitleName());

        } else if (holder instanceof GridHolder) {
            GridHolder gridHolder = (GridHolder) holder;
            gridHolder.title.setText(entity.getGirdName());
        } else if (holder instanceof CardHolder) {
            CardHolder cardHolder = (CardHolder) holder;
            cardHolder.roleName.setText(entity.getRoleName());
            cardHolder.roleJob.setText(entity.getRoleJob());
            Picasso.with(mContext)
                    .load(entity.getRoleImageUrl())
                    .config(Bitmap.Config.RGB_565)
                    .resize(200, 300)
                    .centerInside()
                    .noFade()
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
        TextView roleName;
        TextView roleJob;

        public CardHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.role_img);
            roleName = (TextView) itemView.findViewById(R.id.role_name);
            roleJob = (TextView) itemView.findViewById(R.id.role_job);
        }
    }
}
