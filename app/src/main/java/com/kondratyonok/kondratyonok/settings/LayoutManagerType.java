package com.kondratyonok.kondratyonok.settings;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kondratyonok.kondratyonok.Entry;
import com.kondratyonok.kondratyonok.adapter.GridAdapter;
import com.kondratyonok.kondratyonok.adapter.ListAdapter;

import java.util.List;

/**
 * Created by NKondratyonok on 04.02.18.
 */

public class LayoutManagerType {
    public static final String GRID = "0";
    public static final String LINEAR = "1";

    public static final String DEFAULT = GRID;

    static RecyclerView.LayoutManager getLayoutManager(String code, Activity activity) {
        switch (code) {
            case "0": return new GridLayoutManager(
                    activity,
                    activity.getResources().getInteger(SettingsActivity.getLayoutColumnsId(activity)));
            case "1": return new LinearLayoutManager(activity);
            default: return LayoutManagerType.getLayoutManager(LayoutManagerType.DEFAULT, activity);
        }
    }

    static RecyclerView.Adapter<RecyclerView.ViewHolder> getAdapter(String code, List<Entry> data) {
        switch (code) {
            case "0": return new GridAdapter(data);
            case "1": return new ListAdapter(data);
            default: return LayoutManagerType.getAdapter(LayoutManagerType.DEFAULT, data);
        }
    }
}
