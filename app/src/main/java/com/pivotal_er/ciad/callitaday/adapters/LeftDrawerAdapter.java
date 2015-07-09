package com.pivotal_er.ciad.callitaday.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pivotal_er.ciad.callitaday.R;

public class LeftDrawerAdapter extends RecyclerView.Adapter<LeftDrawerAdapter.ViewHolder> {

    private String[] mTextTitles;
    private TypedArray mIcons;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public LeftDrawerAdapter(String[] titles, TypedArray icons) {
        mTextTitles = titles;
        mIcons = icons;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTextView;
        private ImageView mIconView;
        private Context mContext;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            mContext = context;

            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            mIconView = (ImageView) itemView.findViewById(R.id.rowIcon);
            mTextView = (TextView) itemView.findViewById(R.id.rowText);
        }

        @Override
        public void onClick(View v) {
            if(mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    @Override
    public LeftDrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_list_item, parent, false);
        return new ViewHolder(view, parent.getContext());
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

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

}
