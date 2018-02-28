package com.kondratyonok.kondratyonok.launcher.adapter;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kondratyonok.kondratyonok.model.Entry;
import com.kondratyonok.kondratyonok.utils.CommonUtils;
import com.kondratyonok.kondratyonok.utils.Holder;
import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.utils.DatabaseUtils;
import com.kondratyonok.kondratyonok.helper.ItemTouchHelperAdapter;
import com.kondratyonok.kondratyonok.helper.OnStartDragListener;
import com.kondratyonok.kondratyonok.listener.OnApplicationClickListener;

/**
 * @author Paul Burke (ipaulpro)
 */
public class DesktopGridAdapter extends RecyclerView.Adapter<Holder.ApplicationsHolder>
        implements ItemTouchHelperAdapter {
    @NonNull
    private SparseArray<Entry> data = new SparseArray<>();
    private final int size;
    private final OnStartDragListener mDragStartListener;
    private Activity activity;

    public DesktopGridAdapter(Activity activity, OnStartDragListener dragStartListener, int size) {
        mDragStartListener = dragStartListener;
        this.activity = activity;
        this.size = size;
    }

    @Override
    public Holder.ApplicationsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_desktop, parent, false);
        return new Holder.ApplicationsHolder(view);
    }

    @Override
    public void onBindViewHolder(final Holder.ApplicationsHolder gridHolder, final int position) {
        final int pos = gridHolder.getAdapterPosition();
        if (pos == 0) {
            setTrashHolder(gridHolder);
        } else if (data.get(pos, null) != null) {
            setApplicationHolder(gridHolder, pos);
        } else {
            setEmptyHolder(gridHolder);
        }
    }

    private void setTrashHolder(final Holder.ApplicationsHolder gridHolder) {
        gridHolder.getIconView().setBackground(activity.getResources().getDrawable(R.mipmap.trash));
        gridHolder.getHolder().setOnClickListener(null);
        gridHolder.getHolder().setOnLongClickListener(null);
    }

    private void setApplicationHolder(final Holder.ApplicationsHolder gridHolder, final int pos) {
        try {
            gridHolder.getIconView().setBackground(activity.getPackageManager().getApplicationIcon(data.get(pos).packageName));
            gridHolder.getHolder().setOnClickListener(new OnApplicationClickListener(data.get(pos).packageName, activity.getApplication()));
            gridHolder.getHolder().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mDragStartListener.onStartDrag(gridHolder);
                    return false;
                }
            });
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setEmptyHolder(final Holder.ApplicationsHolder gridHolder) {
        gridHolder.getIconView().setBackground(null);
        gridHolder.getHolder().setOnClickListener(null);
        gridHolder.getHolder().setOnLongClickListener(null);
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
                DatabaseUtils.saveSparseArray(data, activity.getApplication());
            }
        })).start();
    }

    @Override
    public boolean onItemMove(final int fromPosition, final int toPosition) {
        if ((toPosition >= size)||(fromPosition >= size)) {
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
        return size;
    }

    public void setData(SparseArray<Entry> data) {
        this.data = data;
    }
}
