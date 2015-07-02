package com.pivotal_er.ciad.callitaday.fragments;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pivotal_er.ciad.callitaday.R;
import com.pivotal_er.ciad.callitaday.navigationdrawer.LeftDrawerAdapter;

public class LeftDrawerFragment extends android.app.Fragment {

    private RecyclerView mMenuRecyclerView;
    private RecyclerView.Adapter mMenuRecyclerViewAdapter;
    private RecyclerView.LayoutManager mMenuRecyclerLayoutManager;
    private String[] mMenuTitles;
    private TypedArray mMenuIcons;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left_drawer, container, false);

        return view;
    }

    public void setup() {
        mMenuRecyclerView = (RecyclerView) getActivity().findViewById(R.id.left_drawer);
        mMenuRecyclerView.setHasFixedSize(true);
        mMenuTitles = getResources().getStringArray(R.array.array_menu_titles);
        mMenuIcons = getResources().obtainTypedArray(R.array.array_menu_icons);

        mMenuRecyclerViewAdapter = new LeftDrawerAdapter(mMenuTitles, mMenuIcons);
        mMenuRecyclerView.setAdapter(mMenuRecyclerViewAdapter);

        mMenuRecyclerLayoutManager = new LinearLayoutManager(getActivity());
        mMenuRecyclerView.setLayoutManager(mMenuRecyclerLayoutManager);
    }
}
