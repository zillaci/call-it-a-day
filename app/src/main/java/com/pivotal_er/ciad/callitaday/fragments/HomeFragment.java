package com.pivotal_er.ciad.callitaday.fragments;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.pivotal_er.ciad.callitaday.R;
import com.pivotal_er.ciad.callitaday.adapters.HomeImageAdapter;
import com.pivotal_er.ciad.callitaday.database.DatabaseHelper;
import com.pivotal_er.ciad.callitaday.enums.FragmentPage;
import com.pivotal_er.ciad.callitaday.model.DaySummary;
import com.pivotal_er.ciad.callitaday.model.TabRecord;
import com.pivotal_er.ciad.callitaday.utils.CiadTimeUtil;

import org.joda.time.DateTime;

public class HomeFragment extends Fragment {

    private enum WorkStat {
        ON_WORK, OFF_WORK
    }

    private WorkStat mWorkStat = WorkStat.OFF_WORK;
    private final String DATEFORMAT_H_MM_A = "h:mm a";
    private final String DATEFORMAT_YYYY_MM_DD = "yyyyMMdd";

    // Image, Text Infomation
    private RecyclerView mRecyclerView;
    private ImageButton mImgBtnChangeStat;
    private ImageView mImgViewWorkText;
    private ImageView mImgViewCallitText;
    private TextView mTextViewWorkTimeRecord;
    private TextView mTextViewCallitTimeRecord;
    private TextView mTextViewWorkTimeAmount;

    private TypedArray mLauncherIcons;

    private DatabaseHelper mDbHelper = null;
    private RuntimeExceptionDao<TabRecord, Long> mTabRecordDao = null;
    private RuntimeExceptionDao<DaySummary, String> mDaySummaryDao = null;

    private HomeImageAdapter mRecyclerViewAdapter;
    private RecyclerView.LayoutManager mRecyclerViewLayoutManager;
    private OnLauncherItemSelectedListener mListener;

    public interface OnLauncherItemSelectedListener {
        void onLauncherItemSelected(FragmentPage page);
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        mLauncherIcons = getResources().obtainTypedArray(R.array.array_home_icons);
        mRecyclerViewAdapter = new HomeImageAdapter(mLauncherIcons);
        mRecyclerViewAdapter.setOnItemClickListener(new HomeImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mListener.onLauncherItemSelected(FragmentPage.fromPosition(position));
            }
        });
        mRecyclerViewLayoutManager = new GridLayoutManager(getActivity(), 3);

        mDbHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        mTabRecordDao = mDbHelper.getRuntimeExceptionDao(TabRecord.class);
        mDaySummaryDao = mDbHelper.getRuntimeExceptionDao(DaySummary.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.launcher_grid_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(mRecyclerViewLayoutManager);

        mImgBtnChangeStat = (ImageButton) view.findViewById(R.id.image_button_change_stat);
        mImgBtnChangeStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTime now = DateTime.now();
                String date = now.toString(DATEFORMAT_YYYY_MM_DD);

                mTabRecordDao.create(new TabRecord(date, now.getMillis()));

                if(mWorkStat == WorkStat.OFF_WORK) {
                    mDaySummaryDao.create(new DaySummary(date, now.getMillis(), 0, 0));
                }
                else {
                    DaySummary daySummary = mDaySummaryDao.queryForId(date);
                    daySummary.setEndMillis(now.getMillis());
                    daySummary.setAmountMillis(CiadTimeUtil.calcWorkMillisWithBreak(
                            now.getMillis() - daySummary.getStartMillis()));

                    mDaySummaryDao.update(daySummary);
                }

                setTimeContents();
            }
        });

        //set text, image information components
        mImgViewWorkText = (ImageView) view.findViewById(R.id.image_view_work_text);
        mImgViewCallitText = (ImageView) view.findViewById(R.id.image_view_callit_text);
        mTextViewWorkTimeRecord = (TextView) view.findViewById(R.id.text_view_work_time_record);
        mTextViewCallitTimeRecord = (TextView) view.findViewById(R.id.text_view_callit_time_record);
        mTextViewWorkTimeAmount = (TextView) view.findViewById(R.id.text_view_work_time_amount);

        setTimeContents();

        return view;
}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (OnLauncherItemSelectedListener) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnLauncerItemSelectedListener");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(mDbHelper != null) {
            OpenHelperManager.releaseHelper();
            mDbHelper = null;
        }
    }

    public void setTimeContents() {
        DateTime now = DateTime.now();

        DaySummary daySummary = mDaySummaryDao.queryForId(now.toString(DATEFORMAT_YYYY_MM_DD));

        //No data today
        if(daySummary == null) {
            mWorkStat = WorkStat.OFF_WORK;
            mImgViewWorkText.setImageResource(-1);
            mTextViewWorkTimeRecord.setText("");
            mImgViewCallitText.setImageResource(-1);
            mTextViewCallitTimeRecord.setText("");
            mImgBtnChangeStat.setImageResource(R.drawable.work);
            mTextViewWorkTimeAmount.setText("");
        }
        else {
            mWorkStat = WorkStat.ON_WORK;
            mImgBtnChangeStat.setImageResource(R.drawable.callit);
            mImgViewWorkText.setImageResource(R.drawable.work_text);

            DateTime workStartTime = new DateTime(daySummary.getStartMillis());
            DateTime workEndTime = new DateTime(daySummary.getEndMillis());

            mTextViewWorkTimeRecord.setText(workStartTime.toString(DATEFORMAT_H_MM_A));

            if(daySummary.isValidWork()) {
                mImgViewCallitText.setImageResource(R.drawable.callit_text);
                mTextViewCallitTimeRecord.setText(workEndTime.toString(DATEFORMAT_H_MM_A));
                mTextViewWorkTimeAmount.setText(CiadTimeUtil.millisToHourAndMinute(daySummary.getAmountMillis()));
            }
            else {
                mImgViewCallitText.setImageResource(-1);
                mTextViewCallitTimeRecord.setText("");
                mTextViewWorkTimeAmount.setText("");
            }
        }
    }
}
