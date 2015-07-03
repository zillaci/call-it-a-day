package com.pivotal_er.ciad.callitaday.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by sds on 2015-07-03.
 */
public class ImageAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private TypedArray mImages;

    public ImageAdapter(Context ctx, TypedArray images) {
        mContext = ctx;
        mImages = images;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);


        }

        @Override
        public void onClick(View view) {

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView view = new ImageView(parent.getContext());
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mImages.getIndexCount();
    }
}
