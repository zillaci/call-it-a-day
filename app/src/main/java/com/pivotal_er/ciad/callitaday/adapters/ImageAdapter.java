package com.pivotal_er.ciad.callitaday.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pivotal_er.ciad.callitaday.MainActivity;
import com.pivotal_er.ciad.callitaday.R;

/**
 * Created by sds on 2015-07-03.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private Context mContext;
    private TypedArray mImages;

    public ImageAdapter(Context ctx, TypedArray images) {
        mContext = ctx;
        mImages = images;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mImageView;
        private Context mContext;

        public ViewHolder(View itemView, Context ctx) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.launcher_grid_item);
            mContext = ctx;
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            MainActivity mainActivity = (MainActivity) mContext;
            mainActivity.replaceContent(getAdapterPosition());
        }
    }

    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_grid_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, parent.getContext());

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ImageAdapter.ViewHolder holder, int position) {
        holder.mImageView.setImageResource(mImages.getResourceId(position, -1));
    }

    @Override
    public int getItemCount() {
        return mImages.length();
    }
}
