package com.pivotal_er.ciad.callitaday.fragments;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pivotal_er.ciad.callitaday.R;
import com.pivotal_er.ciad.callitaday.enums.FragmentPage;
import com.pivotal_er.ciad.callitaday.navigationdrawer.LeftDrawerAdapter;

public class LeftDrawerFragment extends Fragment {

    private RecyclerView mMenuRecyclerView;
    private LeftDrawerAdapter mMenuRecyclerViewAdapter;
    private RecyclerView.LayoutManager mMenuRecyclerLayoutManager;
    private OnLeftDrawerClickListener mOnLeftDrawerClickListener;

    private String[] mMenuTitles;
    private TypedArray mMenuIcons;

    public interface OnLeftDrawerClickListener {
        public void onLeftDrawerClick(FragmentPage page);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left_drawer, container, false);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mOnLeftDrawerClickListener = (OnLeftDrawerClickListener) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnLauncerItemSelectedListener");
        }
    }

    public void setup() {
        mMenuRecyclerView = (RecyclerView) getActivity().findViewById(R.id.left_drawer);
        mMenuRecyclerView.setHasFixedSize(true);

        mMenuTitles = getResources().getStringArray(R.array.array_menu_titles);
        mMenuIcons = getResources().obtainTypedArray(R.array.array_menu_icons);

        mMenuRecyclerViewAdapter = new LeftDrawerAdapter(mMenuTitles, mMenuIcons);
        mMenuRecyclerView.setAdapter(mMenuRecyclerViewAdapter);
        mMenuRecyclerViewAdapter.setOnItemClickListener(new LeftDrawerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mOnLeftDrawerClickListener.onLeftDrawerClick(FragmentPage.fromPosition(position));
            }
        });

        mMenuRecyclerLayoutManager = new LinearLayoutManager(getActivity());
        mMenuRecyclerView.setLayoutManager(mMenuRecyclerLayoutManager);
    }
}
