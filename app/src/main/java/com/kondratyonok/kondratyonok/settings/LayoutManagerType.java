package com.kondratyonok.kondratyonok.settings;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.kondratyonok.kondratyonok.fragment.main.GridFragment;
import com.kondratyonok.kondratyonok.fragment.main.LinearFragment;

/**
 * Created by NKondratyonok on 04.02.18.
 */

public class LayoutManagerType {
    public static final String GRID = "0";
    public static final String LINEAR = "1";

    static final String DEFAULT = GRID;

    private static String temp = null;

    private static final String TAG = "Settings";

    static Fragment getLayoutFragment(String code) {
        if (temp == null) {
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
        } else {
            switch (temp) {
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

    public static String getName(String code) {
        switch (code) {
            case GRID: return "grid";
            case LINEAR: return "linear";
            default: return getName(DEFAULT);
        }
    }

    public static void setTemp(String code) {
        temp = code;
    }
}
