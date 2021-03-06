package com.kondratyonok.kondratyonok.launcher.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.kondratyonok.kondratyonok.launcher.fragment.ApplicationsFragment;
import com.kondratyonok.kondratyonok.launcher.fragment.DesktopFragment;

/**
 * Created by NKondratyonok on 11.02.18.
 */

public class MainPagerAdapter extends FragmentStatePagerAdapter {
    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return DesktopFragment.newInstance();
            case 1: return ApplicationsFragment.newInstance();
            default: return DesktopFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
