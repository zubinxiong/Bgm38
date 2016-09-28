package me.ewriter.bangumitv.ui.progress.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.ewriter.bangumitv.BangumiApp;
import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.api.entity.AnimeEpEntity;
import me.ewriter.bangumitv.ui.progress.ProgressActivity;
import me.ewriter.bangumitv.utils.LogUtil;

/**
 * Created by Zubin on 2016/9/28.
 */

public class EpAdapter extends RecyclerView.Adapter<EpAdapter.ViewHolder> {

    List<AnimeEpEntity> mList;
    private TextView mEpsTitle, mEpsSummary;
    private BottomSheetAdapter mBottomSheetAdapter;
    private BottomSheetDialog mBottomSheetDialog;
    Context mContext;

    public EpAdapter(Context context) {
        mContext = context;
        setUpBottomSheetDialog(context);
    }

    /** 初始化 bottomSheet */
    private void setUpBottomSheetDialog(Context context) {
        mBottomSheetDialog = new BottomSheetDialog(context);
        View mContentView = LayoutInflater.from(context).inflate(R.layout.view_bottom_sheet, null, false);
        RecyclerView mRecyclerView = (RecyclerView) mContentView.findViewById(R.id.bottom_recyclerView);
        String[] nameArray = context.getResources().getStringArray(R.array.bottom_sheet_name);
        List<String> mNameList = new ArrayList<>();
        for (int i = 0; i < nameArray.length; i++) {
            mNameList.add(nameArray[i]);
        }
        mBottomSheetAdapter = new BottomSheetAdapter(context, mNameList);
        mRecyclerView.setAdapter(mBottomSheetAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        mBottomSheetDialog.setContentView(mContentView);

        mEpsTitle = (TextView) mContentView.findViewById(R.id.eps_title);
        mEpsSummary = (TextView) mContentView.findViewById(R.id.eps_summary);

        // 解决BottomSheetDialog 滑动后无法再次显示
        View view = mBottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
        final BottomSheetBehavior behavior = BottomSheetBehavior.from(view);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    mBottomSheetDialog.dismiss();
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    @Override
    public EpAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_ep_item, parent, false);
        return new EpAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final EpAdapter.ViewHolder holder, final int position) {
        // FIXME: 2016/9/28 如果把操作全部放在adapter 里面似乎和 mvp 的初衷相违背，是否需要把 adpter 也作为 presenter或者view？
        final AnimeEpEntity entity = mList.get(position);

        holder.textView.setText(entity.getDisplayName());

        String epStatus = entity.getEpStatusName();

        if (epStatus.equals("Queue")) {
            // 想看
            holder.textView.setTextColor(BangumiApp.sAppCtx.getResources().getColor(R.color.white));
            holder.textView.setBackground(BangumiApp.sAppCtx.getResources().getDrawable(R.drawable.shape_square_queue));
            holder.textView.getPaint().setFlags(0);
        } else if (epStatus.equals("Drop")) {
            // 弃番
            holder.textView.setTextColor(BangumiApp.sAppCtx.getResources().getColor(R.color.white));
            holder.textView.setBackground(BangumiApp.sAppCtx.getResources().getDrawable(R.drawable.shape_square_watched));
            holder.textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        } else if (epStatus.equals("Watched")) {
            // 看过
            holder.textView.setTextColor(BangumiApp.sAppCtx.getResources().getColor(R.color.white));
            holder.textView.setBackground(BangumiApp.sAppCtx.getResources().getDrawable(R.drawable.shape_square_watched));
            holder.textView.getPaint().setFlags(0);
        } else if (epStatus.equals("NA")) {
            // 未放送
            holder.textView.setTextColor(BangumiApp.sAppCtx.getResources().getColor(R.color.white));
            holder.textView.setBackground(BangumiApp.sAppCtx.getResources().getDrawable(R.drawable.shape_square_disable));
            holder.textView.getPaint().setFlags(0);
        } else {
            // 默认状态
            holder.textView.setTextColor(mContext.getResources().getColor(R.color.calendar_title_color));
            holder.textView.setBackground(mContext.getResources().getDrawable(R.drawable.shape_square_normal));
            holder.textView.getPaint().setFlags(0);
        }

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d(LogUtil.ZUBIN, "gridclick position = " + position);
                updateBottomSheet(entity);
                mBottomSheetDialog.show();
            }
        });
    }

    private void updateBottomSheet(final AnimeEpEntity entity) {
        mEpsTitle.setText(entity.getEpName());
        mEpsSummary.setText(entity.getInfo());

        final String[] valueArray = mContext.getResources().getStringArray(R.array.bottom_sheet_value);
        final String[] type = {"Queue", "Watched", "Drop", ""};

        mBottomSheetAdapter.setOnItemClickListener(new BottomSheetAdapter.onItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                mBottomSheetDialog.dismiss();
                ProgressActivity activity = (ProgressActivity) mContext;
                activity.showProgressDialog();
                activity.getPresenter().updateEpStatus(Integer.parseInt(entity.getEpId()), valueArray[position]);

                entity.setEpStatusName(type[position]);
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(List<AnimeEpEntity> list) {
        mList = list;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;

        }
    }
}