package com.kondratyonok.kondratyonok.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kondratyonok.kondratyonok.Entry;
import com.kondratyonok.kondratyonok.Holder;
import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.Utils;
import com.kondratyonok.kondratyonok.listener.OnApplicationClickListener;
import com.kondratyonok.kondratyonok.listener.OnApplicationsLongClickListener;
import com.kondratyonok.kondratyonok.settings.SettingsActivity;

import java.util.Collections;
import java.util.List;

/**
 * Created by NKondratyonok on 04.02.18.
 */

public class LinearAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static String TAG = "LinearAdapter";

    @NonNull
    private final List<Entry> data;

    private final Activity activity;

    public LinearAdapter(Activity activity) {
        this.data = Utils.getEntriesList(activity);
        Collections.sort(this.data, SettingsActivity.getSortingMethod(activity));
        this.activity = activity;
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
        gridHolder.getIconView().setBackground(data.get(position).icon);

        gridHolder.getTitleView().setText(data.get(position).name);

        String subtitle = data.get(position).packageName +
                "\n" +
                activity.getResources().getString(R.string.launched) +
                " " +
                data.get(position).launched +
                " " +
                activity.getResources().getString(R.string.times);
        gridHolder.getSubtitleView().setText(subtitle);

        gridHolder.getHolder().setOnClickListener(new OnApplicationClickListener(data.get(position)));
        gridHolder.getHolder().setOnLongClickListener(new OnApplicationsLongClickListener(data.get(position)));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
