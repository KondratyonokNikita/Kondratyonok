package com.kondratyonok.kondratyonok.greeting;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.kondratyonok.kondratyonok.greeting.fragment.AgreeFragment;
import com.kondratyonok.kondratyonok.greeting.fragment.DescriptionFragment;
import com.kondratyonok.kondratyonok.greeting.fragment.GreetingFragment;
import com.kondratyonok.kondratyonok.greeting.fragment.LayoutFragment;
import com.kondratyonok.kondratyonok.greeting.fragment.ThemeFragment;
import com.kondratyonok.kondratyonok.utils.CommonUtils;

import java.util.List;

/**
 * Created by NKondratyonok on 03.02.18.
 */

public class GreetingPagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "GreetingPagerAdapter";

    @NonNull
    private final List<Fragment> mData;

    GreetingPagerAdapter(@NonNull final FragmentManager fm,
                         @NonNull final List<Fragment> data) {
        super(fm);
        CommonUtils.log(TAG, "constructor");
        mData = data;
    }

    @Override
    public Fragment getItem(int position) {
        CommonUtils.log(TAG, "getItem " + String.valueOf(position));
        return mData.get(position);
    }

    @Override
    public int getCount() {
        CommonUtils.log(TAG, "getCount");
        return mData.size();
    }
}
