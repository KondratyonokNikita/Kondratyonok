package com.kondratyonok.kondratyonok.settings;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kondratyonok.kondratyonok.Entry;
import com.kondratyonok.kondratyonok.OffsetItemDecoration;
import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.adapter.GridAdapter;
import com.kondratyonok.kondratyonok.adapter.ListAdapter;
import com.kondratyonok.kondratyonok.fragment.main.GridFragment;
import com.kondratyonok.kondratyonok.fragment.main.LinearFragment;

import java.util.List;

/**
 * Created by NKondratyonok on 04.02.18.
 */

public class LayoutManagerType {
    public static final String GRID = "0";
    public static final String LINEAR = "1";

    public static final String DEFAULT = GRID;

    static Fragment getLayoutFragment(String code) {
        switch (code) {
            case "0": return GridFragment.newInstance();
            case "1": return LinearFragment.newInstance();
            default: return LayoutManagerType.getLayoutFragment(LayoutManagerType.DEFAULT);
        }
    }
}
