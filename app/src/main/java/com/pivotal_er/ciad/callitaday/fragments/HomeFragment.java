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

import com.pivotal_er.ciad.callitaday.R;
import com.pivotal_er.ciad.callitaday.adapters.HomeImageAdapter;
import com.pivotal_er.ciad.callitaday.enums.FragmentPage;

/**
 * Created by sds on 2015-07-02.
 */
public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private HomeImageAdapter mRecyclerViewAdapter;
    private RecyclerView.LayoutManager mRecyclerViewLayoutManager;
    private TypedArray mLauncherIcons;
    private OnLauncherItemSelectedListener mListener;

    public interface OnLauncherItemSelectedListener {
        public void onLauncerItemSelected(FragmentPage page);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mLauncherIcons = getResources().obtainTypedArray(R.array.array_home_icons);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.launcher_grid_view);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerViewAdapter = new HomeImageAdapter(mLauncherIcons);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerViewAdapter.setOnItemClickListener(new HomeImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mListener.onLauncerItemSelected(FragmentPage.fromPosition(position));
            }
        });

        mRecyclerViewLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mRecyclerViewLayoutManager);

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

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }
}
