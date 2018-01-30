package com.kondratyonok.kondratyonok.launcher;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kondratyonok.kondratyonok.R;

class Holder {

    static class GridHolder extends RecyclerView.ViewHolder {

        private final View mImageView;
        private final TextView mTextView;

        GridHolder(final View view) {
            super(view);

            mImageView = view.findViewById(R.id.launcher_image);
            mTextView = view.findViewById(R.id.color);
        }

        View getImageView() {
            return mImageView;
        }

        TextView getTextView() { return mTextView; }
    }
}
