package com.kondratyonok.kondratyonok.settings;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.kondratyonok.kondratyonok.fragment.main.GridFragment;
import com.kondratyonok.kondratyonok.fragment.main.LinearFragment;

/**
 * Created by NKondratyonok on 04.02.18.
 */

public class LayoutManagerType {
    private static final String GRID = "0";
    private static final String LINEAR = "1";

    static final String DEFAULT = GRID;

    private static final String TAG = "Settings";

    static Fragment getLayoutFragment(String code) {
        switch (code) {
            case LayoutManagerType.GRID:
                Log.i(TAG, "grid type");
                return GridFragment.newInstance();
            case LayoutManagerType.LINEAR:
                Log.i(TAG, "linear type");
                return LinearFragment.newInstance();
            default:
                Log.i(TAG, "default type");
                return LayoutManagerType.getLayoutFragment(LayoutManagerType.DEFAULT);
        }
    }
}
