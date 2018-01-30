package com.kondratyonok.kondratyonok.launcher;

import android.graphics.Color;
import android.graphics.ColorSpace;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kondratyonok.kondratyonok.LauncherActivity;
import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.data.Storage;

import java.util.List;

public class LauncherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
                Snackbar.make(v, "color = " + colorRRGGBB + ", text = " + mData.get(position).text, Snackbar.LENGTH_SHORT)
                        .setDuration(5000)
                        .setAction("DELETE", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mData.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        }).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
