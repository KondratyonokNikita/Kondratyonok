package com.kondratyonok.kondratyonok.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kondratyonok.kondratyonok.R;

/**
 * Created by NKondratyonok on 30.01.18.
 */

public class Holder {

    static class GridHolder extends RecyclerView.ViewHolder {

        private final View mImageView;
        private final TextView colorTextView;
        private final TextView textTextView;

        GridHolder(final View view) {
            super(view);

            mImageView = view.findViewById(R.id.launcher_image);
            colorTextView = view.findViewById(R.id.color);
            textTextView = view.findViewById(R.id.qaz);
        }

        View getImageView() {
            return mImageView;
        }

        TextView getColorTextView() { return colorTextView; }

        TextView getTextTextView() { return textTextView; }
    }

}
