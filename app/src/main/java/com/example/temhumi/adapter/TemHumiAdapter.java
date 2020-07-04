package com.example.temhumi.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.temhumi.fragment.ChartTemHumiFragment;
import com.example.temhumi.fragment.InforTemHumiFragment;

public class TemHumiAdapter extends FragmentPagerAdapter {
    private InforTemHumiFragment inforTemHumiFragment;
    private ChartTemHumiFragment chartTemHumiFragment;

    public TemHumiAdapter(@NonNull FragmentManager fm, InforTemHumiFragment inforTemHumiFragment, ChartTemHumiFragment chartTemHumiFragment) {
        super(fm);
        this.inforTemHumiFragment = inforTemHumiFragment;
        this.chartTemHumiFragment = chartTemHumiFragment;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if (position == 0) {
            if (inforTemHumiFragment == null) inforTemHumiFragment = new InforTemHumiFragment();
            fragment = inforTemHumiFragment;
        } else {
            if (chartTemHumiFragment == null) chartTemHumiFragment = new ChartTemHumiFragment();
            fragment = chartTemHumiFragment;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
