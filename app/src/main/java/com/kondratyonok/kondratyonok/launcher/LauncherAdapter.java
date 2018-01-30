package com.kondratyonok.kondratyonok.launcher;

import android.graphics.Color;
import android.graphics.ColorSpace;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kondratyonok.kondratyonok.LauncherActivity;
import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.data.Storage;

import java.util.List;

public class LauncherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static String TAG = "LauncherAdapter";

    @NonNull
    private final Storage mData;

    public LauncherAdapter(@NonNull final Storage data) {
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new Holder.GridHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        bindGridView((Holder.GridHolder) holder, position);
    }

    private void bindGridView(@NonNull final Holder.GridHolder gridHolder, final int position) {
        final View view = gridHolder.getImageView();
        view.setBackgroundColor(mData.get(position).color);
        final TextView color = gridHolder.getTextView();
        final String colorRRGGBB = String.format("#%06X", 0xFFFFFF & mData.get(position).color);
        color.setText(colorRRGGBB);
        final LauncherAdapter adapter = this;
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                Snackbar snackbar = Snackbar.make(v, "color = " + colorRRGGBB + ", text = " + mData.get(position).text, Snackbar.LENGTH_SHORT)
                        .setDuration(5000)
                        .setAction("DELETE", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mData.remove(position);
                                if (Log.isLoggable(TAG, Log.INFO)) {
                                    Log.i(TAG, "Deleted from position " + position + " with color " + colorRRGGBB);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });
                snackbar.addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        final String description;
                        switch (event) {
                            case Snackbar.Callback.DISMISS_EVENT_ACTION: description = "via an action click."; break;
                            case Snackbar.Callback.DISMISS_EVENT_CONSECUTIVE: description = "from a new Snackbar being shown."; break;
                            case Snackbar.Callback.DISMISS_EVENT_MANUAL: description = "via a call to dismiss()."; break;
                            case Snackbar.Callback.DISMISS_EVENT_SWIPE: description = "via a swipe."; break;
                            case Snackbar.Callback.DISMISS_EVENT_TIMEOUT: description = "via a timeout."; break;
                            default: description = "by god.";
                        }
                        if (Log.isLoggable(TAG, Log.INFO)) {
                            Log.i(TAG, "SnackBar dismissed " + description);
                        }
                    }
                });
                snackbar.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
