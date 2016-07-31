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
import com.squareup.picasso.Transformation;

import java.util.List;

import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.dao.BangumiCalendar;

/**
 * Created by Zubin on 2016/7/27.
 */
public class CalendarItemAdapter extends RecyclerView.Adapter<CalendarItemAdapter.MyViewHolder> {

    private List<BangumiCalendar> mList;
    private Context mContext;

    public CalendarItemAdapter(Context context, List<BangumiCalendar> calenderList) {
        mContext = context;
        mList = calenderList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        BangumiCalendar calendar = mList.get(position);

        holder.mBangumiTitle.setText(calendar.getName_cn());
        holder.mBangumiOutline.setText(calendar.getBangumi_total() + "; " + calendar.getBangumi_average());
        if (calendar.getLarge_image() != null) {
            Picasso.with(mContext)
                    .load(calendar.getLarge_image())
                    .placeholder(R.drawable.img_on_load)
                    .config(Bitmap.Config.RGB_565)
                    .fit()
                    .error(R.drawable.img_on_error)
                    .into(holder.mBangumiImg);
        } else {
            Picasso.with(mContext)
                    .load(R.drawable.img_on_error)
                    .placeholder(R.drawable.img_on_load)
                    .into(holder.mBangumiImg);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mBangumiImg;
        TextView mBangumiTitle;
        TextView mBangumiOutline;
        View card;

        public MyViewHolder(View itemView) {
            super(itemView);
            card = itemView;
            mBangumiImg = (ImageView) itemView.findViewById(R.id.calendar_bangumi_img);
            mBangumiTitle = (TextView) itemView.findViewById(R.id.calendar_bangumi_title);
            mBangumiOutline = (TextView) itemView.findViewById(R.id.calendar_bangumi_outline);
            card.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // TODO: 2016/7/27

        }
    }

    Transformation transformation = new Transformation() {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth() / 2, source.getHeight() / 2);

            Bitmap result = Bitmap.createScaledBitmap(source, size, size, false);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return "transformation_square";
        }
    };
}
