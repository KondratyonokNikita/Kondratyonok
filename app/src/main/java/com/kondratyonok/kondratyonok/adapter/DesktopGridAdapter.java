package com.kondratyonok.kondratyonok.adapter;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kondratyonok.kondratyonok.database.Entry;
import com.kondratyonok.kondratyonok.Holder;
import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.Utils;
import com.kondratyonok.kondratyonok.database.EntryDbHolder;
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
        try {
            gridHolder.getIconView().setBackground(activity.getPackageManager().getApplicationIcon(data.get(position).packageName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        gridHolder.getTitleView().setText(data.get(position).name);
        gridHolder.getHolder().setOnClickListener(new OnApplicationClickListener(data.get(position).packageName, activity.getApplication()));
        gridHolder.getHolder().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mDragStartListener.onStartDrag(gridHolder);
                Log.e("DRAAAAAAAG", "12");
                return false;
            }
        });
    }

    @Override
    public void onItemDismiss(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMoveEnd() {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                if (data.get(data.size() - 1).desktopPosition != -1) {
                    data.get(data.size() - 1).desktopPosition = -1;
                    EntryDbHolder.getInstance().getDb(activity.getApplicationContext()).calculationResultDao().update(data.get(data.size() - 1));
                }
                for (int i = 0; i < data.size() - 1; ++i) {
                    if (data.get(i).desktopPosition != i) {
                        data.get(i).desktopPosition = i;
                        EntryDbHolder.getInstance().getDb(activity.getApplicationContext()).calculationResultDao().update(data.get(i));
                    }
                }
            }
        })).start();
    }

    @Override
    public boolean onItemMove(final int fromPosition, final int toPosition) {
        Collections.swap(data, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Entry> data) {
        this.data = data.subList(0, 20);
    }
}
