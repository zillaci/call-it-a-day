package com.pivotal_er.ciad.callitaday.adapters;

import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pivotal_er.ciad.callitaday.R;
import com.pivotal_er.ciad.callitaday.model.DaySummary;
import com.pivotal_er.ciad.callitaday.utils.CiadTimeUtil;

import org.joda.time.DateTime;

import java.util.List;

public class AdjustAdapter extends RecyclerView.Adapter<AdjustAdapter.ViewHolder> {

    public enum WorkTimeType {START, END}
    private TypedArray mWeekDayImages;
    private List<DaySummary> mDaySummaries;
    private OnTimeClickListener mOnTimeClickListener;

    private final String DATEFORMAT_H_MM_A = "h:mm a";

    public AdjustAdapter(TypedArray images, List<DaySummary> daySummaries) {
        mWeekDayImages = images;
        mDaySummaries = daySummaries;
    }

    public void setOnTimeClickListener(OnTimeClickListener onTimeClickListener) {
        mOnTimeClickListener = onTimeClickListener;
    }

    public interface OnTimeClickListener {
        void onTimeClick(WorkTimeType type, DaySummary daySummary);
    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

    @Override
    public AdjustAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weekdays_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DaySummary daySummary = mDaySummaries.get(position);

        DateTime start = daySummary.getStartMillis() != 0 ? new DateTime(daySummary.getStartMillis()) : null;
        DateTime end = daySummary.getEndMillis() != 0 ? new DateTime(daySummary.getEndMillis()) : null;

        holder.mImgViewWeekday.setImageResource(mWeekDayImages.getResourceId(position, -1));
        holder.mTextViewStart.setText(start != null ? start.toString(DATEFORMAT_H_MM_A) : "");
        holder.mTextViewEnd.setText(end != null ? end.toString(DATEFORMAT_H_MM_A) : "");
        holder.mTextViewAmount.setText(start == null || end == null ? "" : CiadTimeUtil.millisToHourAndMinute(daySummary.getAmountMillis()));
    }

    @Override
    public int getItemCount() {
        return mWeekDayImages.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImgViewWeekday;
        private TextView mTextViewStart;
        private TextView mTextViewEnd;
        private TextView mTextViewAmount;


        public ViewHolder(View itemView) {
            super(itemView);

            mImgViewWeekday = (ImageView) itemView.findViewById(R.id.image_view_weekday);
            mTextViewStart = (TextView) itemView.findViewById(R.id.text_view_start);
            mTextViewEnd = (TextView) itemView.findViewById(R.id.text_view_end);
            mTextViewAmount = (TextView) itemView.findViewById(R.id.text_view_amount);

            mTextViewStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnTimeClickListener.onTimeClick(WorkTimeType.START, mDaySummaries.get(getAdapterPosition()));
                }
            });

            mTextViewEnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnTimeClickListener.onTimeClick(WorkTimeType.END, mDaySummaries.get(getAdapterPosition()));
                }
            });
        }
    }
    public List<DaySummary> getDaySummaries() {
        return mDaySummaries;
    }

    public void changeItem(DaySummary daySummary) {
        notifyItemChanged(mDaySummaries.indexOf(daySummary));
    }
}
