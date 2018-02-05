package com.kondratyonok.kondratyonok.settings;

import android.support.v4.app.Fragment;

import com.kondratyonok.kondratyonok.fragment.main.GridFragment;
import com.kondratyonok.kondratyonok.fragment.main.LinearFragment;

/**
 * Created by NKondratyonok on 04.02.18.
 */

public class LayoutManagerType {
    public static final String GRID = "0";
    public static final String LINEAR = "1";

    public static final String DEFAULT = GRID;

    static Fragment getLayoutFragment(String code) {
        switch (code) {
            case LayoutManagerType.GRID: return GridFragment.newInstance();
            case LayoutManagerType.LINEAR: return LinearFragment.newInstance();
            default: return LayoutManagerType.getLayoutFragment(LayoutManagerType.DEFAULT);
        }
    }
}
