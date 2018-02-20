package com.kondratyonok.kondratyonok.fragment.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.kondratyonok.kondratyonok.OffsetItemDecoration;
import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.adapter.DesktopGridAdapter;
import com.kondratyonok.kondratyonok.database.Entry;
import com.kondratyonok.kondratyonok.database.EntryViewModel;
import com.kondratyonok.kondratyonok.helper.OnStartDragListener;
import com.kondratyonok.kondratyonok.helper.SimpleItemTouchHelperCallback;
import com.kondratyonok.kondratyonok.settings.SettingsActivity;
import com.yandex.metrica.YandexMetrica;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NKondratyonok on 11.02.18.
 */

public class DesktopFragment extends Fragment implements OnStartDragListener {

    private ItemTouchHelper mItemTouchHelper;

    public DesktopFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("ON_CREATE_VIEW_DESKTOP", "CREATE");
        YandexMetrica.reportEvent("Fragment", "{\"fragment\":\"main\":\"grid\"}");
        YandexMetrica.reportEvent("ViewEvent","{\"Layout\":\"grid\"}");

        View mainView = inflater.inflate(R.layout.fr_desktop, container, false);

        final int spanCount = getResources().getInteger(SettingsActivity.getLayoutColumnsId(getActivity()));
        final RecyclerView recyclerView = mainView.findViewById(R.id.desktop);

        DisplayMetrics displayMetrics = new DisplayMetrics();

        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        int itemWidth = width/spanCount;
        int rawCount = height/itemWidth;

        final DesktopGridAdapter adapter = new DesktopGridAdapter(getActivity(), this, spanCount * rawCount);

        EntryViewModel calculationViewModel = ViewModelProviders.of(this).get(EntryViewModel.class);
        calculationViewModel.getCalculatingLiveData().observe(this, new Observer<List<Entry>>() {
            @Override
            public void onChanged(@Nullable final List<Entry> calculationResults) {
                SparseArray<Entry> desktopEntries = new SparseArray<>();
                for (Entry entry: calculationResults) {
                    if (entry.desktopPosition != -1) {
                        desktopEntries.append(entry.desktopPosition, entry);
                    }
                }
                adapter.setData(desktopEntries);
                adapter.notifyDataSetChanged();
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new OffsetItemDecoration(getActivity().getResources().getDimensionPixelSize(R.dimen.item_offset)));

        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount);
        recyclerView.setLayoutManager(layoutManager);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter, getActivity());
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        return mainView;
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    public static DesktopFragment newInstance() {
        return new DesktopFragment();
    }

}
