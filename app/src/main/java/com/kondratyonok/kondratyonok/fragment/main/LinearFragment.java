package com.kondratyonok.kondratyonok.fragment.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kondratyonok.kondratyonok.Entry;
import com.kondratyonok.kondratyonok.R;
import com.kondratyonok.kondratyonok.activity.ApplicationsActivity;
import com.kondratyonok.kondratyonok.adapter.LinearAdapter;

import java.util.List;

/**
 * Created by NKondratyonok on 05.02.18.
 */

public class LinearFragment extends Fragment {
    private static final int ID = R.layout.fr_recycler_view;
    private List<Entry> data;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("ON_CREATE_VIEW_LINEAR", "CREATE");
        final View mainView = inflater.inflate(ID, container, false);

        RecyclerView recyclerView = mainView.findViewById(R.id.content);
        recyclerView.setHasFixedSize(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(new LinearAdapter(getActivity()));

        ((ApplicationsActivity)getActivity()).getNavigationView().getMenu().findItem(R.id.nav_list).setChecked(true);
        ((ApplicationsActivity)getActivity()).getNavigationView().getMenu().findItem(R.id.nav_grid).setChecked(false);
        return mainView;
    }

    public static LinearFragment newInstance() {
        return new LinearFragment();
    }

}
