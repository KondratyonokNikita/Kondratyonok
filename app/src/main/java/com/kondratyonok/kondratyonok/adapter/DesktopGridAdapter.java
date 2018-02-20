package com.kondratyonok.kondratyonok.adapter;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Paul Burke (ipaulpro)
 */
public class DesktopGridAdapter extends RecyclerView.Adapter<Holder.ApplicationsHolder>
        implements ItemTouchHelperAdapter {

    @NonNull
    private SparseArray<Entry> data = new SparseArray<>();

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
    public void onBindViewHolder(final Holder.ApplicationsHolder gridHolder, final int position) {
        if (data.get(position, null) != null) {
            Log.i("onBind", String.valueOf(position));
            try {
                gridHolder.getIconView().setBackground(activity.getPackageManager().getApplicationIcon(data.get(position).packageName));
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            gridHolder.getTitleView().setText(data.get(position).name);
            gridHolder.getHolder().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("onClick", String.valueOf(position));
                }
            });
//            gridHolder.getHolder().setOnClickListener(new OnApplicationClickListener(data.get(position).packageName, activity.getApplication()));
            gridHolder.getHolder().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mDragStartListener.onStartDrag(gridHolder);
                    Log.e("DRAAAAAAAG", "12");
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
    public void onItemMoveEnd() {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                if (data.get(0, null) != null) {
                    data.get(0).desktopPosition = -1;
                }
                for (int i = 0; i < data.size(); ++i) {
                    int key = data.keyAt(i);
                    if (key == 0) {
                        continue;
                    }
                    if (data.get(key, null) != null) {
                        if (data.get(key).desktopPosition != key) {
                            data.get(key).desktopPosition = key;
                        }
                    }
                }
                Utils.saveSparseArray(data, activity.getApplication());
            }
        })).start();
        Log.i("onMoveEnd", String.valueOf(data.size()));
    }

    @Override
    public boolean onItemMove(final int fromPosition, final int toPosition) {
        Log.i("move", String.valueOf(fromPosition) + " + " + String.valueOf(toPosition));
        if ((toPosition > 20)||(fromPosition > 20)) {
            return false;
        }
        Entry from = data.get(fromPosition, null);
        Entry to = data.get(toPosition, null);
        data.delete(fromPosition);
        data.delete(toPosition);
        if (to != null) {
            data.append(fromPosition, to);
        }
        if (from != null) {
            data.append(toPosition, from);
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public void setData(SparseArray<Entry> data) {
        Log.i("setData", String.valueOf(data.size()));
        this.data = data;
    }
}
