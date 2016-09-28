package me.ewriter.bangumitv.ui.persons.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import me.ewriter.bangumitv.R;

/**
 * Created by Zubin on 2016/9/28.
 */

public class PersonItemAdapter extends RecyclerView.Adapter<PersonItemAdapter.ViewHolder> {

    List<PersonItem> mlist;

    public PersonItemAdapter() {
        this.mlist = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item , parent, false);
        return new PersonItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        PersonItem personItem = mlist.get(position);

        holder.name.setText(personItem.name);
        holder.info.setText(personItem.info);

        Picasso.with(holder.name.getContext())
                .load(personItem.imageUrl)
                .fit()
                .centerCrop()
                .error(R.drawable.img_on_error)
                .placeholder(R.drawable.img_on_load)
                .into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public void setList(List<PersonItem> list) {
        mlist = list;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView info;
        ImageView avatar;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.person_name);
            info = (TextView) itemView.findViewById(R.id.person_info);
            avatar = (ImageView) itemView.findViewById(R.id.person_img);
        }
    }
}
