package com.example.user.fbandgooglelogin.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.fbandgooglelogin.R;
import com.example.user.fbandgooglelogin.adapter.ViewPagerAdapter;

/**
 * Created by User on 4/5/2017.
 */

public class VIewPagerfragment extends Fragment {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.view_pager_fragment,container,false);
        mViewPager=(ViewPager)view.findViewById(R.id.view_pager);
        mTabLayout=(TabLayout)view.findViewById(R.id.tab_layout);
        mViewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
        return view;
    }
}
