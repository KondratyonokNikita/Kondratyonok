package com.kondratyonok.kondratyonok.launcher.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kondratyonok.kondratyonok.OffsetItemDecoration;
import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.launcher.adapter.DesktopGridAdapter;
import com.kondratyonok.kondratyonok.model.Entry;
import com.kondratyonok.kondratyonok.database.EntryViewModel;
import com.kondratyonok.kondratyonok.helper.OnStartDragListener;
import com.kondratyonok.kondratyonok.helper.SimpleItemTouchHelperCallback;
import com.kondratyonok.kondratyonok.settings.SettingsActivity;
import com.yandex.metrica.YandexMetrica;

import java.util.List;

/**
 * Created by NKondratyonok on 11.02.18.
 */

public class DesktopFragment extends Fragment implements OnStartDragListener {

    private ItemTouchHelper mItemTouchHelper;
    private DesktopGridAdapter adapter;
    private RecyclerView recyclerView;
    private int rowCount;
    private int columnCount;

    public DesktopFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fr_desktop, container, false);

        recyclerView = mainView.findViewById(R.id.desktop);
        adapter = new DesktopGridAdapter(getActivity(), this, rowCount * columnCount);
        rowCount = getRowCount();
        columnCount = getColumnCount();
        initEntryViewModel();
        initRecyclerView();
        initItemTouchHelper();
        return mainView;
    }

    private void initItemTouchHelper() {
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new OffsetItemDecoration(getActivity().getResources().getDimensionPixelSize(R.dimen.item_offset)));

        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), columnCount);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initEntryViewModel() {
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
    }

    private int getColumnCount() {
        return getResources().getInteger(SettingsActivity.getLayoutColumnsId(getActivity()));
    }

    private int getRowCount() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int itemWidth = displayMetrics.widthPixels/getColumnCount();
        return displayMetrics.heightPixels/itemWidth;
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    public static DesktopFragment newInstance() {
        return new DesktopFragment();
    }

}
