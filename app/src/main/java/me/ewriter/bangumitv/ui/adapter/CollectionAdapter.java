package me.ewriter.bangumitv.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.api.response.MyCollection;

/**
 * Created by Zubin on 2016/8/4.
 */
public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.MyHolder> {

    private Context mContext;
    private List<MyCollection> mList;
    private onCollectionItemListener listener;

    public CollectionAdapter(Context mContext, List<MyCollection> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public CollectionAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_my_collection_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final CollectionAdapter.MyHolder holder, int position) {
        final MyCollection myCollection = mList.get(position);
        if (TextUtils.isEmpty(myCollection.getSubject().getName_cn())) {
            holder.name.setText(myCollection.getName());
        } else {
            holder.name.setText(myCollection.getSubject().getName_cn());
        }

        String total = myCollection.getSubject().getEps() == 0 ? "??" : (myCollection.getSubject().getEps() + "");
        String myStatus = myCollection.getEp_status() + "";

        holder.progressText.setText(myStatus + " / " + total);
        if (total.equals("??")) {
            holder.progressBar.setMax(999);
        } else {
            holder.progressBar.setMax(Integer.parseInt(total));
        }
        holder.progressBar.setProgress(Integer.parseInt(myStatus));
        holder.progressBar.getProgressDrawable().setColorFilter(
                mContext.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        if (myCollection.getSubject().getImages() != null && myCollection.getSubject().getImages().getLarge() != null) {
            Picasso.with(mContext).load(myCollection.getSubject().getImages().getLarge())
                    .fit()
                    .centerCrop()
                    .config(Bitmap.Config.RGB_565)
                    .placeholder(R.drawable.img_on_load)
                    .error(R.drawable.img_on_error)
                    .into(holder.image);
        } else {
            Picasso.with(mContext)
                    .load(R.drawable.img_on_error)
                    .into(holder.image);
        }

        int air_day = myCollection.getSubject().getAir_weekday();
        String text = "放送时间: ";
        if (air_day != 0) {
            switch (air_day) {
                case 1:
                    text += "周一";
                    break;
                case 2:
                    text += "周二";
                    break;
                case 3:
                    text += "周三";
                    break;
                case 4:
                    text += "周四";
                    break;
                case 5:
                    text += "周五";
                    break;
                case 6:
                    text += "周六";
                    break;
                case 7:
                    text += "周日";
                    break;

            }
        }
        holder.airDayText.setText(text);

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCollectionClick(holder.image, myCollection);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface onCollectionItemListener {
        void onCollectionClick(View view, MyCollection collection);
    }

    public void setonCollectionClcik(onCollectionItemListener listener) {
        this.listener = listener;
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView name;
        private TextView progressText;
        private View item;
        private ProgressBar progressBar;
        private TextView airDayText;

        public MyHolder(View itemView) {
            super(itemView);
            item = itemView;
            image = (ImageView) itemView.findViewById(R.id.collection_img);
            name = (TextView) itemView.findViewById(R.id.collection_title);
            progressText = (TextView) itemView.findViewById(R.id.collection_progress_text);
            progressBar = (ProgressBar) itemView.findViewById(R.id.collection_progress);
            airDayText = (TextView) itemView.findViewById(R.id.air_day);
        }
    }
}
