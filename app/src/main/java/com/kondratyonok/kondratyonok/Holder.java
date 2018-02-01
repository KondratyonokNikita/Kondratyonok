package com.kondratyonok.kondratyonok;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

/**
 * Created by NKondratyonok on 30.01.18.
 */

public class Holder {

    public static class ListHolder extends RecyclerView.ViewHolder {

        private final View mImageView;
        private final TextView colorTextView;
        private final TextView textTextView;
        private final View mWholeView;

        public ListHolder(final View view) {
            super(view);

            mImageView = view.findViewById(R.id.launcher_image);
            colorTextView = view.findViewById(R.id.color);
            textTextView = view.findViewById(R.id.qaz);
            mWholeView = view.findViewById(R.id.list_holder);
        }

        public View getImageView() {
            return mImageView;
        }

        public TextView getColorTextView() { return colorTextView; }

        public TextView getTextTextView() { return textTextView; }

        public View getWholeView() { return mWholeView; }
    }

    public static class GridHolder extends RecyclerView.ViewHolder {

        private final View mImageView;
        private final TextView mTextView;
        private final View mWholeView;

        public GridHolder(final View view) {
            super(view);

            mImageView = view.findViewById(R.id.launcher_image);
            mTextView = view.findViewById(R.id.color);
            mWholeView = view.findViewById(R.id.grid_holder);
        }

        public View getImageView() {
            return mImageView;
        }

        public TextView getTextView() { return mTextView; }

        public View getWholeView() {
            return mWholeView;
        }
    }

    public static class ApplicationsHolder extends RecyclerView.ViewHolder {

        private final View mImageView;
        private final TextView mTextView;
        private final View mWholeView;

        public ApplicationsHolder(final View view) {
            super(view);

            mImageView = view.findViewById(R.id.launcher_image);
            mTextView = view.findViewById(R.id.color);
            mWholeView = view.findViewById(R.id.application_holder);
        }

        public View getImageView() {
            return mImageView;
        }

        public TextView getTextView() { return mTextView; }

        public View getWholeView() {
            return mWholeView;
        }
    }
}
