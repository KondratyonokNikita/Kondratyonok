package com.kondratyonok.kondratyonok.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kondratyonok.kondratyonok.OffsetItemDecoration;
import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.adapter.DesktopGridAdapter;
import com.kondratyonok.kondratyonok.helper.OnStartDragListener;
import com.kondratyonok.kondratyonok.helper.SimpleItemTouchHelperCallback;
import com.kondratyonok.kondratyonok.settings.SettingsActivity;
import com.yandex.metrica.YandexMetrica;

/**
 * Created by NKondratyonok on 11.02.18.
 */

public class DesktopFragment extends Fragment implements OnStartDragListener {

    private ItemTouchHelper mItemTouchHelper;

    public DesktopFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return new RecyclerView(container.getContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.i("ON_CREATE_VIEW_DESKTOP", "CREATE");
        YandexMetrica.reportEvent("Fragment", "{\"fragment\":\"main\":\"grid\"}");
        YandexMetrica.reportEvent("ViewEvent","{\"Layout\":\"grid\"}");

        super.onViewCreated(view, savedInstanceState);

        final DesktopGridAdapter adapter = new DesktopGridAdapter(getActivity(), this);

        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new OffsetItemDecoration(getActivity().getResources().getDimensionPixelSize(R.dimen.item_offset)));
        final int spanCount = getResources().getInteger(SettingsActivity.getLayoutColumnsId(getActivity()));
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount);
        recyclerView.setLayoutManager(layoutManager);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    public static DesktopFragment newInstance() {
        return new DesktopFragment();
    }

}
