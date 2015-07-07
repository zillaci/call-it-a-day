package com.pivotal_er.ciad.callitaday.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pivotal_er.ciad.callitaday.R;

/**
 * Created by sds on 2015-07-03.
 */
public class HomeImageAdapter extends RecyclerView.Adapter<HomeImageAdapter.ViewHolder> {

    private TypedArray mImages;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public HomeImageAdapter(TypedArray images) {
        mImages = images;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mImageView;

        public ViewHolder(View itemView, Context ctx) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.launcher_grid_item);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    @Override
    public HomeImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_grid_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, parent.getContext());

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HomeImageAdapter.ViewHolder holder, int position) {
        holder.mImageView.setImageResource(mImages.getResourceId(position, -1));
    }

    @Override
    public int getItemCount() {
        return mImages.length();
    }

    public void setOnItemClickListener(final OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
