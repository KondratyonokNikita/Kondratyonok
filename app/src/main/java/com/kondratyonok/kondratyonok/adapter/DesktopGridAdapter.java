package com.kondratyonok.kondratyonok.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kondratyonok.kondratyonok.Entry;
import com.kondratyonok.kondratyonok.Holder;
import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.Utils;
import com.kondratyonok.kondratyonok.helper.ItemTouchHelperAdapter;
import com.kondratyonok.kondratyonok.helper.ItemTouchHelperViewHolder;
import com.kondratyonok.kondratyonok.helper.OnStartDragListener;
import com.kondratyonok.kondratyonok.listener.OnApplicationClickListener;
import com.kondratyonok.kondratyonok.listener.OnApplicationsLongClickListener;
import com.kondratyonok.kondratyonok.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.Arrays;
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

    public DesktopGridAdapter(Activity activity, OnStartDragListener dragStartListener) {
        mDragStartListener = dragStartListener;
        this.data.addAll(Utils.getEntriesList(activity).subList(0, 10));
        Collections.sort(this.data, SettingsActivity.getSortingMethod(activity));
    }

    @Override
    public Holder.ApplicationsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid, parent, false);
        return new Holder.ApplicationsHolder(view);
    }

    @Override
    public void onBindViewHolder(final Holder.ApplicationsHolder gridHolder, int position) {
        gridHolder.getIconView().setBackground(data.get(position).icon);

        gridHolder.getTitleView().setText(data.get(position).name);

        // Start a drag whenever the handle view it touched
        gridHolder.getHolder().setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(gridHolder);
                }
                return false;
            }
        });

        gridHolder.getHolder().setOnClickListener(new OnApplicationClickListener(data.get(position)));
        gridHolder.getHolder().setOnLongClickListener(new OnApplicationsLongClickListener(data.get(position)));
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
