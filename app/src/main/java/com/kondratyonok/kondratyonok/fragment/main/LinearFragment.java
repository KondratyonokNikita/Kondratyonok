package com.kondratyonok.kondratyonok.fragment.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.activity.ApplicationsActivity;
import com.kondratyonok.kondratyonok.adapter.LinearAdapter;
import com.kondratyonok.kondratyonok.database.Entry;
import com.kondratyonok.kondratyonok.database.EntryViewModel;
import com.yandex.metrica.YandexMetrica;

import java.util.List;

/**
 * Created by NKondratyonok on 05.02.18.
 */

public class LinearFragment extends Fragment {
    private static final int ID = R.layout.fr_recycler_view;

    private LinearAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("ON_CREATE_VIEW_LINEAR", "CREATE");
        YandexMetrica.reportEvent("Fragment", "{\"fragment\":\"main\":\"linear\"}");
        YandexMetrica.reportEvent("ViewEvent","{\"Layout\":\"linear\"}");
        final View mainView = inflater.inflate(ID, container, false);

        recyclerView = mainView.findViewById(R.id.content);
        recyclerView.setHasFixedSize(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new LinearAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        ((ApplicationsActivity)getActivity()).getNavigationView().getMenu().findItem(R.id.nav_list).setChecked(true);
        ((ApplicationsActivity)getActivity()).getNavigationView().getMenu().findItem(R.id.nav_grid).setChecked(false);

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
        adapter = new LinearAdapter(getActivity());
        recyclerView.setAdapter(adapter);
    }

    public static LinearFragment newInstance() {
        return new LinearFragment();
    }

}
