package com.kondratyonok.kondratyonok.fragment.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.RadioButton;

import com.kondratyonok.kondratyonok.Entry;
import com.kondratyonok.kondratyonok.OffsetItemDecoration;
import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.activity.ApplicationsActivity;
import com.kondratyonok.kondratyonok.adapter.GridAdapter;
import com.kondratyonok.kondratyonok.fragment.greeting.LayoutFragment;
import com.kondratyonok.kondratyonok.settings.Layout;
import com.kondratyonok.kondratyonok.settings.SettingsActivity;

import java.util.List;

/**
 * Created by NKondratyonok on 05.02.18.
 */

public class GridFragment extends Fragment {
    private static final int ID = R.layout.fr_recycler_view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("ON_CREATE_VIEW_GRID", "CREATE");
        final View mainView = inflater.inflate(ID, container, false);

        RecyclerView recyclerView = mainView.findViewById(R.id.content);
        recyclerView.setHasFixedSize(false);

        recyclerView.addItemDecoration(new OffsetItemDecoration(getActivity().getResources().getDimensionPixelSize(R.dimen.item_offset)));
        recyclerView.setLayoutManager(new GridLayoutManager(
                getActivity(),
                getActivity().getResources().getInteger(SettingsActivity.getLayoutColumnsId(getActivity()))));

        recyclerView.setAdapter(new GridAdapter(getActivity()));

        ((ApplicationsActivity)getActivity()).getNavigationView().getMenu().findItem(R.id.nav_grid).setChecked(true);
        ((ApplicationsActivity)getActivity()).getNavigationView().getMenu().findItem(R.id.nav_list).setChecked(false);

        return mainView;
    }

    public static GridFragment newInstance() {
        return new GridFragment();
    }

}
