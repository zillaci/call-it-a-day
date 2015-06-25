package com.pivotal_er.ciad.callitaday.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.pivotal_er.ciad.callitaday.R;

public class LeftDrawerFragment extends Fragment {

    private ListView mDrawerList;
    private String[] mMenuTitles;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left_drawer, container, false);
        mDrawerList = (ListView) view.findViewById(R.id.left_drawer);
        mMenuTitles = getResources().getStringArray(R.array.array_menu_titles);
        mDrawerList.setAdapter(new ArrayAdapter<String>(getActivity().getBaseContext(), R.layout.drawer_list_item, mMenuTitles));

        return view;
    }
}
