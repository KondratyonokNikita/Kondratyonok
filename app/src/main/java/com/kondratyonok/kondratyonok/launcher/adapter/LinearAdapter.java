package com.kondratyonok.kondratyonok.launcher.adapter;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kondratyonok.kondratyonok.model.Entry;
import com.kondratyonok.kondratyonok.utils.Holder;
import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.listener.OnApplicationClickListener;
import com.kondratyonok.kondratyonok.listener.OnApplicationsLongClickListener;
import com.kondratyonok.kondratyonok.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by NKondratyonok on 04.02.18.
 */

public class LinearAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static String TAG = "LinearAdapter";

    private List<Entry> data;

    private final Activity activity;

    public LinearAdapter(Activity activity) {
        this.activity = activity;
        this.setData(new ArrayList<Entry>());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new Holder.ApplicationsHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        bindGridView((Holder.ApplicationsHolder) holder, position);
    }

    private void bindGridView(@NonNull final Holder.ApplicationsHolder gridHolder, final int position) {
        try {
            gridHolder.getIconView().setBackground(activity.getPackageManager().getApplicationIcon(data.get(position).packageName));
            gridHolder.getTitleView().setText(data.get(position).name);
            String subtitle = activity.getResources().getString(R.string.launched) + " " +
                    data.get(position).launched +
                    " " + activity.getResources().getString(R.string.times) + "\n" +
                    data.get(position).packageName;
            gridHolder.getSubtitleView().setText(subtitle);
            gridHolder.getHolder().setOnClickListener(new OnApplicationClickListener(data.get(position).packageName, activity.getApplication()));
            gridHolder.getHolder().setOnLongClickListener(new OnApplicationsLongClickListener(data.get(position), activity));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
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
