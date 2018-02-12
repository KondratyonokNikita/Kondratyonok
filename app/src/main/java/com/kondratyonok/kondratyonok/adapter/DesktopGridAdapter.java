package com.kondratyonok.kondratyonok.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kondratyonok.kondratyonok.Holder;
import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.helper.ItemTouchHelperAdapter;
import com.kondratyonok.kondratyonok.helper.ItemTouchHelperViewHolder;
import com.kondratyonok.kondratyonok.helper.OnStartDragListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Paul Burke (ipaulpro)
 */
public class RecyclerListAdapter extends RecyclerView.Adapter<Holder.ItemViewHolder>
        implements ItemTouchHelperAdapter {

    private final List<String> mItems = new ArrayList<>();

    private final OnStartDragListener mDragStartListener;

    public RecyclerListAdapter(Context context, OnStartDragListener dragStartListener) {
        mDragStartListener = dragStartListener;
        mItems.addAll(Arrays.asList(context.getResources().getStringArray(R.array.dummy_items)));
    }

    @Override
    public Holder.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        return new Holder.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final Holder.ItemViewHolder holder, int position) {
        holder.textView.setText(mItems.get(position));

        // Start a drag whenever the handle view it touched
        holder.handleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }

    @Override
    public void onItemDismiss(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mItems, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
