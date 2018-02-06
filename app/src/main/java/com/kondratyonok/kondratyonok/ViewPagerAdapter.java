package com.kondratyonok.kondratyonok;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.kondratyonok.kondratyonok.fragment.greeting.AgreeFragment;
import com.kondratyonok.kondratyonok.fragment.greeting.DescriptionFragment;
import com.kondratyonok.kondratyonok.fragment.greeting.GreetingFragment;
import com.kondratyonok.kondratyonok.fragment.greeting.LayoutFragment;
import com.kondratyonok.kondratyonok.fragment.greeting.ThemeFragment;

import java.util.List;

/**
 * Created by NKondratyonok on 03.02.18.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    @NonNull
    private final List<Integer> mData;

    public ViewPagerAdapter(@NonNull final FragmentManager fm,
                            @NonNull final List<Integer> data) {
        super(fm);
        mData = data;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return GreetingFragment.newInstance(mData.get(position));
            case 1:
                return DescriptionFragment.newInstance(mData.get(position));
            case 2:
                return ThemeFragment.newInstance(mData.get(position));
            case 3:
                return LayoutFragment.newInstance(mData.get(position));
            case 4:
                return AgreeFragment.newInstance(mData.get(position));
            default:
                return GreetingFragment.newInstance(mData.get(position));
        }
    }

    @Override
    public int getCount() {
        return mData.size();
    }
}
