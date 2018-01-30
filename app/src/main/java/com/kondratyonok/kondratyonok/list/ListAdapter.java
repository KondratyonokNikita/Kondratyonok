package com.kondratyonok.kondratyonok.list;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.data.Storage;

import java.util.List;

/**
 * Created by NKondratyonok on 30.01.18.
 */

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @NonNull
    private final Storage mData;

    public ListAdapter(@NonNull final Storage data) {
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new Holder.GridHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        bindGridView((Holder.GridHolder) holder, position);
    }

    private void bindGridView(@NonNull final Holder.GridHolder gridHolder, final int position) {
        final View view = gridHolder.getImageView();
        Drawable background = view.getBackground();
        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable)background).getPaint().setColor(mData.get(position).color);
        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable)background).setColor(mData.get(position).color);
        } else if (background instanceof ColorDrawable) {
            ((ColorDrawable)background).setColor(mData.get(position).color);
        }
        final TextView color = gridHolder.getColorTextView();
        final String colorRRGGBB = String.format("#%06X", 0xFFFFFF & mData.get(position).color);
        color.setText(colorRRGGBB);

        final TextView text = gridHolder.getTextTextView();
        text.setText(mData.get(position).text);

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                Snackbar.make(v, "color = " + colorRRGGBB, Snackbar.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


}
