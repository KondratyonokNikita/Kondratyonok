package com.kondratyonok.kondratyonok.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.kondratyonok.kondratyonok.Database;
import com.kondratyonok.kondratyonok.Entry;
import com.kondratyonok.kondratyonok.Holder;
import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.Utils;
import com.kondratyonok.kondratyonok.activity.ApplicationsActivity;
import com.kondratyonok.kondratyonok.listener.OnApplicationClickListener;
import com.kondratyonok.kondratyonok.listener.OnApplicationsLongClickListener;

import java.util.List;

/**
 * Created by NKondratyonok on 04.02.18.
 */

public class GridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static String TAG = "ApplicationsAdapter";

    @NonNull
    private final List<Entry> data;

    public GridAdapter(Activity activity) {
        this.data = Utils.getEntriesList(activity);
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
        gridHolder.getIconView().setBackground(data.get(position).icon);

        gridHolder.getTitleView().setText(data.get(position).name);

        gridHolder.getHolder().setOnClickListener(new OnApplicationClickListener(data.get(position)));
        gridHolder.getHolder().setOnLongClickListener(new OnApplicationsLongClickListener(data.get(position)));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

