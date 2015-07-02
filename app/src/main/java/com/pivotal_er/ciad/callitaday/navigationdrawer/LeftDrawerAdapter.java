package com.pivotal_er.ciad.callitaday.navigationdrawer;

import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pivotal_er.ciad.callitaday.R;

/**
 * Created by sds on 2015-06-29.
 */
public class LeftDrawerAdapter extends RecyclerView.Adapter<LeftDrawerAdapter.ViewHolder> {

    private String[] mTextTitles;
    private TypedArray mIcons;

    public LeftDrawerAdapter(String[] titles, TypedArray icons) {
        mTextTitles = titles;
        mIcons = icons;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;
        private ImageView mIconView;

        public ViewHolder(View itemView) {
            super(itemView);

            mIconView = (ImageView) itemView.findViewById(R.id.rowIcon);
            mTextView = (TextView) itemView.findViewById(R.id.rowText);
        }
    }

    @Override
    public LeftDrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LeftDrawerAdapter.ViewHolder holder, int position) {
        holder.mTextView.setText(mTextTitles[position]);
        holder.mIconView.setImageResource(mIcons.getResourceId(position, -1));
    }

    @Override
    public int getItemCount() {
        return mTextTitles.length;
    }


}
