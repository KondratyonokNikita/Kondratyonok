package com.kondratyonok.kondratyonok.utils;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.helper.ItemTouchHelperViewHolder;

/**
 * Created by NKondratyonok on 30.01.18.
 */

public class Holder {

    public static class ApplicationsHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

        private final View iconView;
        private final TextView title;
        private final TextView subtitle;
        private final View holder;

        public ApplicationsHolder(final View view) {
            super(view);

            iconView = view.findViewById(R.id.image);
            title = view.findViewById(R.id.title);
            subtitle = view.findViewById(R.id.subtitle);
            holder = view.findViewById(R.id.holder);
        }

        public View getIconView() {
            return iconView;
        }

        public TextView getTitleView() {
            return title;
        }

        public TextView getSubtitleView() { return subtitle; }

        public View getHolder() {
            return holder;
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
}
