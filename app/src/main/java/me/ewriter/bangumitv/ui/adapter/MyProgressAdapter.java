package me.ewriter.bangumitv.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.api.response.BangumiDetail;

/**
 * Created by Zubin on 2016/8/8.
 */
public class MyProgressAdapter extends RecyclerView.Adapter<MyProgressAdapter.MyHolder> {

    private Context mContext;
    private List<BangumiDetail.EpsBean> mList;
    private onGridClick mListener;

    public MyProgressAdapter(Context mContext, List<BangumiDetail.EpsBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_detail_adapter_grid, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        BangumiDetail.EpsBean entity = mList.get(position);
        holder.title.setText((int)entity.getSort() + "");
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface onGridClick {
        void onClick(View view, int position);
    }

    public void setOnGridClickListener(onGridClick listener) {
        mListener = listener;
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView title;

        public MyHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView;
        }
    }
}
