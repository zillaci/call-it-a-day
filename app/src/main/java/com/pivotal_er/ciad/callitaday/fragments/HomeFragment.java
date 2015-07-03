package com.pivotal_er.ciad.callitaday.fragments;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pivotal_er.ciad.callitaday.R;
import com.pivotal_er.ciad.callitaday.adapters.ImageAdapter;

/**
 * Created by sds on 2015-07-02.
 */
public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mRecyclerViewAdapter;
    private RecyclerView.LayoutManager mRecyclerViewLayoutManager;
    private TypedArray mLauncherIcons;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mLauncherIcons = getResources().obtainTypedArray(R.array.array_home_icons);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.launcher_grid_view);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerViewAdapter = new ImageAdapter(getActivity(), mLauncherIcons);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        mRecyclerViewLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mRecyclerViewLayoutManager);

        return view;
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }
}
