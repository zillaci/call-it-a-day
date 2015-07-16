package com.pivotal_er.ciad.callitaday.fragments;

import android.app.TimePickerDialog;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.pivotal_er.ciad.callitaday.R;
import com.pivotal_er.ciad.callitaday.adapters.AdjustAdapter;
import com.pivotal_er.ciad.callitaday.database.DatabaseHelper;
import com.pivotal_er.ciad.callitaday.dialogs.TimePickerDialogFragment;
import com.pivotal_er.ciad.callitaday.model.DaySummary;
import com.pivotal_er.ciad.callitaday.utils.CiadTimeUtil;

import org.joda.time.DateTime;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdjustFragment extends Fragment {

    private TextView mTextViewWeekInfo;
    private TextView mTextViewTotalWorkTime;
    private TextView mTextViewRemainWorkTime;

    //Day Summary List
    private RecyclerView mRecyclerViewWorkTime;
    private AdjustAdapter mAdjustAdapter;
    private RecyclerView.LayoutManager mRecyclerViewLayoutManager;

    private TypedArray mWeekdayImages;

    private DatabaseHelper mDbHelper;
    private RuntimeExceptionDao<DaySummary, String> mDaySummaryDao;

    //private final String date = new DateTime().toString("yyyyMMdd");

    private static final int WORK_HOURS_PER_WEEK = 40;

    public static AdjustFragment newInstance() {
        AdjustFragment fragment = new AdjustFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        mDbHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        mDaySummaryDao = mDbHelper.getRuntimeExceptionDao(DaySummary.class);
        mWeekdayImages = getResources().obtainTypedArray(R.array.array_weekdays);

        final List<DaySummary> daySummaries = getDaySummariesByDay(DateTime.now());

        mAdjustAdapter = new AdjustAdapter(mWeekdayImages, daySummaries);
        mAdjustAdapter.setOnTimeClickListener(new AdjustAdapter.OnTimeClickListener() {

            @Override
            public void onTimeClick(AdjustAdapter.WorkTimeType type, final DaySummary daySummary) {
                TimePickerDialogFragment newFragment = new TimePickerDialogFragment();

                if(type == AdjustAdapter.WorkTimeType.START) {
                    long startMillis = daySummary.getStartMillis();
                    if(startMillis > 0) {
                        newFragment.setTime(startMillis);
                    }

                    newFragment.setListener(new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            daySummary.setStartMillis(CiadTimeUtil.getMillis(daySummary.getDate(), hourOfDay, minute));
                            if(daySummary.isValidWork()) {
                                long amountMillis = CiadTimeUtil.calcWorkMillisWithBreak(
                                        daySummary.getEndMillis() - daySummary.getStartMillis());

                                daySummary.setAmountMillis(amountMillis);
                            }
                            mDaySummaryDao.createOrUpdate(daySummary);
                            mAdjustAdapter.changeItem(daySummary);
                            setTotalWorkTime();
                            setRemainWorkTime();
                        }
                    });
                }
                else if(type == AdjustAdapter.WorkTimeType.END) {
                    long endMillis = daySummary.getEndMillis();
                    if(endMillis > 0) {
                        newFragment.setTime(endMillis);
                    }

                    newFragment.setListener(new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            daySummary.setEndMillis(CiadTimeUtil.getMillis(daySummary.getDate(), hourOfDay, minute));
                            if(daySummary.isValidWork()) {
                                long amountMillis = CiadTimeUtil.calcWorkMillisWithBreak(
                                        daySummary.getEndMillis() - daySummary.getStartMillis());

                                daySummary.setAmountMillis(amountMillis);
                            }
                            mDaySummaryDao.createOrUpdate(daySummary);
                            mAdjustAdapter.changeItem(daySummary);
                            setTotalWorkTime();
                            setRemainWorkTime();
                        }
                    });
                }
                else {
                    throw new IllegalStateException("id is an invalid value");
                }

                newFragment.show(getActivity().getSupportFragmentManager(), "");
            }
        });

        mRecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adjust, container, false);

        mRecyclerViewWorkTime = (RecyclerView) view.findViewById(R.id.recycler_view_weekdays);
        mRecyclerViewWorkTime.setAdapter(mAdjustAdapter);
        mRecyclerViewWorkTime.setLayoutManager(mRecyclerViewLayoutManager);

        mTextViewWeekInfo = (TextView) view.findViewById(R.id.text_view_week_info);
        mTextViewTotalWorkTime = (TextView) view.findViewById(R.id.text_view_total_work_time);
        mTextViewRemainWorkTime = (TextView) view.findViewById(R.id.text_view_remain_work_time);

        initView();

        return view;
    }

    private void initView() {
        mTextViewWeekInfo.setText(CiadTimeUtil.getWeekInfo(DateTime.now()));
        setTotalWorkTime();
        setRemainWorkTime();
    }

    private void setRemainWorkTime() {
        long workMillis = WORK_HOURS_PER_WEEK * 3600 * 1000;
        long totalWorked = calcTotalWorkTime();

        mTextViewRemainWorkTime.setText(CiadTimeUtil.millisToHourAndMinute(workMillis - totalWorked));
    }

    private void setTotalWorkTime() {
        String amountString = CiadTimeUtil.millisToHourAndMinute(calcTotalWorkTime());
        mTextViewTotalWorkTime.setText(amountString);
    }

    private long calcTotalWorkTime() {
        List<DaySummary> daySummaries = mAdjustAdapter.getDaySummaries();
        long amount = 0;
        for(DaySummary daySummary : daySummaries) {
            amount += daySummary.getAmountMillis();
        }

        return amount;
    }

    private List<DaySummary> getDaySummariesByDay(DateTime dateTime) {
        DateTime startDate = CiadTimeUtil.getWeekStartDate(dateTime);
        DateTime endDate = CiadTimeUtil.getWeekEndDate(dateTime);

        final int WORKDAYS = 5;

        List<DaySummary> daySummaries = null;
        try {
            daySummaries = mDaySummaryDao.queryBuilder().orderBy("date", true)
                    .where().between("date", startDate.toString("yyyyMMdd"), endDate.toString("yyyyMMdd")).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(daySummaries == null) {
            daySummaries = new ArrayList<>();
            for(int i = 0; i < WORKDAYS; ++i) {
                DateTime curDate = startDate.plusDays(i);
                DaySummary daySummary = new DaySummary(curDate.toString("yyyyMMdd"), 0, 0, 0);
                daySummaries.add(daySummary);
            }

            return daySummaries;
        }
        else {
            List<DaySummary> rList = new ArrayList<>();

            for(int i = 0; i < WORKDAYS; ++i) {
                String curDateString = startDate.plusDays(i).toString("yyyyMMdd");

                int dateIndex = -1;
                for(DaySummary daySummary : daySummaries) {
                    if(daySummary.getDate().equals(curDateString)) {
                        dateIndex = daySummaries.indexOf(daySummary);
                    }
                }

                if(dateIndex != -1) {
                    rList.add(daySummaries.get(dateIndex));
                }
                else {
                    rList.add(new DaySummary(curDateString, 0, 0, 0));
                }
            }

            return rList;
        }
    }
}
