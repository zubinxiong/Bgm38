package me.ewriter.bangumitv.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Zubin on 2016/8/1.
 */
public class BangumiDetailAdapter extends RecyclerView.Adapter<BangumiDetailAdapter.BangumiDetailHolder> {

    List<String> mList;
    Context mContext;

    public BangumiDetailAdapter(Context context, List list) {
        mContext = context;
        mList = list;
    }

    @Override
    public BangumiDetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new BangumiDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(BangumiDetailHolder holder, int position) {
        holder.test.setText("Text " + position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class BangumiDetailHolder extends RecyclerView.ViewHolder {

        TextView test;

        public BangumiDetailHolder(View itemView) {
            super(itemView);
            test = (TextView) itemView;
        }
    }
}
