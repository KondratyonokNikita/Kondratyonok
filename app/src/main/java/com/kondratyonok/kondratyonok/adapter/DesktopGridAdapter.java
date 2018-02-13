package com.kondratyonok.kondratyonok.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kondratyonok.kondratyonok.database.Entry;
import com.kondratyonok.kondratyonok.Holder;
import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.Utils;
import com.kondratyonok.kondratyonok.helper.ItemTouchHelperAdapter;
import com.kondratyonok.kondratyonok.helper.OnStartDragListener;
import com.kondratyonok.kondratyonok.listener.OnApplicationClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Paul Burke (ipaulpro)
 */
public class DesktopGridAdapter extends RecyclerView.Adapter<Holder.ApplicationsHolder>
        implements ItemTouchHelperAdapter {

    @NonNull
    private List<Entry> data = new ArrayList<>();

    private final OnStartDragListener mDragStartListener;
    private Activity activity;

    public DesktopGridAdapter(Activity activity, OnStartDragListener dragStartListener) {
        mDragStartListener = dragStartListener;
        this.activity = activity;
    }

    @Override
    public Holder.ApplicationsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid, parent, false);
        return new Holder.ApplicationsHolder(view);
    }

    @Override
    public void onBindViewHolder(final Holder.ApplicationsHolder gridHolder, int position) {
        if (data.get(position).icon != null) {
            gridHolder.getIconView().setBackground(data.get(position).icon);
            gridHolder.getTitleView().setText(data.get(position).name);
            gridHolder.getHolder().setOnClickListener(new OnApplicationClickListener(data.get(position).packageName, activity.getApplication()));
            gridHolder.getHolder().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mDragStartListener.onStartDrag(gridHolder);
                    return false;
                }
            });
        }
    }

    @Override
    public void onItemDismiss(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(data, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
