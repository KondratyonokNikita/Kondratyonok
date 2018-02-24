package com.kondratyonok.kondratyonok.launcher.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kondratyonok.kondratyonok.R;
import com.yandex.metrica.YandexMetrica;

/**
 * Created by NKondratyonok on 11.02.18.
 */

public class ApplicationsFragment extends Fragment {
    private static final int ID = R.layout.fr_applications;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View mainView = inflater.inflate(ID, container, false);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.applications, GridFragment.newInstance()).commit();
        return mainView;
    }

    public static ApplicationsFragment newInstance() {
        return new ApplicationsFragment();
    }
}
