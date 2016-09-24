package me.ewriter.bangumitv.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
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

    private onGridClickListener mGridListener;
    private onCardClickListener mCardListener;

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
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
            updateState(gridHolder.title, entity);
            gridHolder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 这里的position 要的是eps 的位置，但实际上的是算上前面两种类型的位置
                    mGridListener.onGridClick(v, position);
                }
            });

        } else if (holder instanceof CardHolder) {
            final CardHolder cardHolder = (CardHolder) holder;
            cardHolder.roleName.setText(entity.getRoleName());
            cardHolder.roleJob.setText(entity.getRoleJob());
            if (entity.getRoleImageUrl() != null) {
                Picasso.with(mContext)
                        .load(entity.getRoleImageUrl())
                        .config(Bitmap.Config.RGB_565)
                        .placeholder(R.drawable.img_on_load)
                        .error(R.drawable.img_on_error)
                        .resize(200, 300)
                        .centerInside()
                        .noFade()
                        .into(cardHolder.image);
            } else {
                Picasso.with(mContext).load(R.drawable.img_on_error).into(cardHolder.image);
            }

            cardHolder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCardListener.onCardClick(cardHolder.image, position);
                }
            });

        }

    }

    private void updateState(TextView textView, BangumiDetailEntity entity) {
        int type = entity.getEpsType();
        String status = entity.getStatus();

        textView.setText(entity.getGirdName());

        if (status.equals("Air")) {
            // AIR 又根据 type 分为 WISH, WATCHED,DROP 和默认
            textView.setEnabled(true);
            if (type == 1) {
                //想看
                textView.setTextColor(mContext.getResources().getColor(R.color.white));
                textView.setBackground(mContext.getResources().getDrawable(R.drawable.shape_square_queue));
                textView.getPaint().setFlags(0);
            } else if (type == 2) {
                // 看过
                textView.setTextColor(mContext.getResources().getColor(R.color.white));
                textView.setBackground(mContext.getResources().getDrawable(R.drawable.shape_square_watched));
                textView.getPaint().setFlags(0);
            } else if (type == 3) {
                // 弃番
                textView.setTextColor(mContext.getResources().getColor(R.color.white));
                textView.setBackground(mContext.getResources().getDrawable(R.drawable.shape_square_watched));
                textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else if (type == 0){
                // 剩下的都是默认状态
                textView.setTextColor(mContext.getResources().getColor(R.color.calendar_title_color));
                textView.setBackground(mContext.getResources().getDrawable(R.drawable.shape_square_normal));
                textView.getPaint().setFlags(0);
            }

        } else if (status.equals("NA") || status.equals("TODAY")) {
            // 未放送，和今天放送 不可点
//            textView.setEnabled(false);
            textView.setTextColor(mContext.getResources().getColor(R.color.white));
            textView.setBackground(mContext.getResources().getDrawable(R.drawable.shape_square_disable));
            textView.getPaint().setFlags(0);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**获取 title 和 card 的个数，已保证 grid 的真实位置*/
    public int getExpGridCount() {
        int count = 0;
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).getType() != TYPE_GRID)
                count++;
        }

        return count;
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
        View item;

        public GridHolder(View itemView) {
            super(itemView);
            item = itemView;
            title = (TextView) itemView;
        }
    }

    public interface onGridClickListener {
        void onGridClick(View view, int position);
    }

    public void setOnGridClickListener(onGridClickListener listener) {
        mGridListener = listener;
    }

    public interface onCardClickListener {
        void onCardClick(View view, int position);
    }

    public void setonCardClickListener(onCardClickListener listener) {
        mCardListener = listener;
    }

    class CardHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView roleName;
        TextView roleJob;
        View item;

        public CardHolder(View itemView) {
            super(itemView);
            item = itemView;
            image = (ImageView) itemView.findViewById(R.id.role_img);
            roleName = (TextView) itemView.findViewById(R.id.role_name);
            roleJob = (TextView) itemView.findViewById(R.id.role_job);
        }
    }
}
