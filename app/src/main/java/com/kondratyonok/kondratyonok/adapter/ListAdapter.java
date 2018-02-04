package com.kondratyonok.kondratyonok.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kondratyonok.kondratyonok.Entry;
import com.kondratyonok.kondratyonok.Holder;
import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.listener.OnApplicationClickListener;
import com.kondratyonok.kondratyonok.listener.OnApplicationsLongClickListener;

import java.util.List;

/**
 * Created by NKondratyonok on 04.02.18.
 */

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static String TAG = "ListAdapter";

    @NonNull
    private final List<Entry> mData;

    public ListAdapter(@NonNull final List<Entry> data) {
        mData = data;
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
        gridHolder.getIconView().setBackground(mData.get(position).icon);

        gridHolder.getTitleView().setText(mData.get(position).name);

        gridHolder.getSubtitleView().setText(mData.get(position).packageName);

        gridHolder.getHolder().setOnClickListener(new OnApplicationClickListener(mData.get(position)));
        gridHolder.getHolder().setOnLongClickListener(new OnApplicationsLongClickListener(mData.get(position)));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}