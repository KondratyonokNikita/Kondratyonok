package com.kondratyonok.kondratyonok.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kondratyonok.kondratyonok.Holder;
import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.listener.OnApplicationClickListener;
import com.kondratyonok.kondratyonok.listener.OnApplicationsLongClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NKondratyonok on 06.02.18.
 */

public class ProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;

    private List<InfoEntry> data = new ArrayList<InfoEntry>() {{
        add(new InfoEntry(R.string.mobile_phone, R.string.mobile, R.drawable.ic_phone));
        add(new InfoEntry(R.string.home_phone, R.string.home, R.drawable.ic_home));
        add(new InfoEntry(R.string.yandex_email, R.string.email, R.drawable.ic_email));
        add(new InfoEntry(R.string.my_address, R.string.address, R.drawable.ic_location_on));
        add(new InfoEntry(R.string.my_vk, R.string.vk, null));
        add(new InfoEntry(R.string.my_instagram, R.string.instagram, null));
        add(new InfoEntry(R.string.my_github, R.string.github, null));
    }};

    public ProfileAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new Holder.ApplicationsHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindGridView((Holder.ApplicationsHolder) holder, position);
    }

    private void bindGridView(@NonNull final Holder.ApplicationsHolder gridHolder, final int position) {
        if (data.get(position).icon != null) {
            gridHolder.getIconView().setBackground(activity.getResources().getDrawable(data.get(position).icon));
        }
        gridHolder.getTitleView().setText(data.get(position).title);

        gridHolder.getSubtitleView().setText(data.get(position).subtitle);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private static class InfoEntry {
        Integer title;
        Integer subtitle;
        Integer icon;

        InfoEntry(Integer title, Integer subtitle, Integer icon) {
            this.title = title;
            this.subtitle = subtitle;
            this.icon = icon;
        }
    }
}
