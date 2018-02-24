package com.kondratyonok.kondratyonok.launcher.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kondratyonok.kondratyonok.OffsetItemDecoration;
import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.launcher.ApplicationsActivity;
import com.kondratyonok.kondratyonok.launcher.adapter.GridAdapter;
import com.kondratyonok.kondratyonok.model.Entry;
import com.kondratyonok.kondratyonok.database.EntryViewModel;
import com.kondratyonok.kondratyonok.settings.SettingsActivity;
import com.yandex.metrica.YandexMetrica;

import java.util.List;

/**
 * Created by NKondratyonok on 05.02.18.
 */

public class GridFragment extends Fragment {
    private static final int ID = R.layout.fr_recycler_view;

    private GridAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("ON_CREATE_VIEW_GRID", "CREATE");
        YandexMetrica.reportEvent("Fragment", "{\"fragment\":\"main\":\"grid\"}");
        YandexMetrica.reportEvent("ViewEvent","{\"Layout\":\"grid\"}");
        final View mainView = inflater.inflate(ID, container, false);

        recyclerView = mainView.findViewById(R.id.content);
        recyclerView.setHasFixedSize(false);

        recyclerView.addItemDecoration(new OffsetItemDecoration(getActivity().getResources().getDimensionPixelSize(R.dimen.item_offset)));
        recyclerView.setLayoutManager(new GridLayoutManager(
                getActivity(),
                getActivity().getResources().getInteger(SettingsActivity.getLayoutColumnsId(getActivity()))));

        adapter = new GridAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        ((ApplicationsActivity)getActivity()).getNavigationView().getMenu().findItem(R.id.nav_grid).setChecked(true);
        ((ApplicationsActivity)getActivity()).getNavigationView().getMenu().findItem(R.id.nav_list).setChecked(false);

        EntryViewModel calculationViewModel = ViewModelProviders.of(this).get(EntryViewModel.class);
        calculationViewModel.getCalculatingLiveData().observe(this, new Observer<List<Entry>>() {
            @Override
            public void onChanged(@Nullable final List<Entry> calculationResults) {
                adapter.setData(calculationResults);
                adapter.notifyDataSetChanged();
            }
        });

        return mainView;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        adapter = new GridAdapter(getActivity());
        recyclerView.setAdapter(adapter);
    }

    public static GridFragment newInstance() {
        return new GridFragment();
    }

}
