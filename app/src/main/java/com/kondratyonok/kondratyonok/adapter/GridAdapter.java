package com.kondratyonok.kondratyonok.adapter;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kondratyonok.kondratyonok.database.Entry;
import com.kondratyonok.kondratyonok.Holder;
import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.Utils;
import com.kondratyonok.kondratyonok.database.EntryDbHolder;
import com.kondratyonok.kondratyonok.listener.OnApplicationClickListener;
import com.kondratyonok.kondratyonok.listener.OnApplicationsLongClickListener;
import com.kondratyonok.kondratyonok.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by NKondratyonok on 04.02.18.
 */

public class GridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static String TAG = "ApplicationsAdapter";

    private List<Entry> data;

    private final Activity activity;

    public GridAdapter(Activity activity) {
        this.activity = activity;
        this.setData(new ArrayList<Entry>());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid, parent, false);
        return new Holder.ApplicationsHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        bindGridView((Holder.ApplicationsHolder) holder, position);
    }

    private void bindGridView(@NonNull final Holder.ApplicationsHolder gridHolder, final int position) {
        try {
            gridHolder.getIconView().setBackground(activity.getPackageManager().getApplicationIcon(data.get(position).packageName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        gridHolder.getTitleView().setText(data.get(position).name);

        gridHolder.getHolder().setOnClickListener(new OnApplicationClickListener(data.get(position).packageName, activity.getApplication()));
        gridHolder.getHolder().setOnLongClickListener(new OnApplicationsLongClickListener(data.get(position)));
    }
    
    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(final List<Entry> data) {
        this.data = data;
        Collections.sort(this.data, SettingsActivity.getSortingMethod(activity));
    }
}

