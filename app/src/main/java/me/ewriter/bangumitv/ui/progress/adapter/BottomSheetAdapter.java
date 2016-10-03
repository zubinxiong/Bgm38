package me.ewriter.bangumitv.ui.progress.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.ewriter.bangumitv.R;

/**
 * Created by Zubin on 2016/8/8.
 */
public class BottomSheetAdapter extends RecyclerView.Adapter<BottomSheetAdapter.BottomSheetHolder> {

    private Context mContext;
    private List<String> mList = new ArrayList<>();
    private onItemClickListener listener;

    public BottomSheetAdapter(Context mContext, List mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public BottomSheetHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.bottom_sheet_text, parent, false);
        return new BottomSheetHolder(view);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(BottomSheetHolder holder, final int position) {
        holder.textView.setText(mList.get(position));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v, position);
            }
        });
    }

    public interface onItemClickListener {
        void onClick(View view, int position);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    class BottomSheetHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public BottomSheetHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }
}
