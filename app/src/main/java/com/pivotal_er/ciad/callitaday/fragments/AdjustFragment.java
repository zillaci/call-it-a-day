package com.pivotal_er.ciad.callitaday.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pivotal_er.ciad.callitaday.R;

/**
 * Created by sds on 2015-07-02.
 */
public class AdjustFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_adjust, container, false);
    }

    public static AdjustFragment newInstance() {
        AdjustFragment fragment = new AdjustFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }
}
